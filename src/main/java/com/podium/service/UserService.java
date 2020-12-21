package com.podium.service;

import com.podium.controller.dto.request.ProfileUpdateControllerRequest;
import com.podium.dal.entity.Country;
import com.podium.dal.entity.PodiumResource;
import com.podium.dal.entity.Role;
import com.podium.dal.entity.User;
import com.podium.controller.dto.other.FileControllerDto;
import com.podium.dal.repository.CountryRepository;
import com.podium.dal.repository.RoleRepository;
import com.podium.dal.repository.UserRepository;
import com.podium.service.dto.request.SignUpServiceDto;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
public class UserService {

    private UserRepository userRepository;

    private RoleService roleService;
    private CountryService countryService;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, RoleService roleService, CountryRepository countryRepository, CountryService countryService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.countryService = countryService;
    }

    @Transactional
    public void addUser(SignUpServiceDto requestDto) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException {

        if(this.userRepository.existsByUsername(requestDto.getUsername()))
            throw new PodiumEntityAlreadyExistException("User with given username");

        if(this.userRepository.existsByEmail(requestDto.getEmail()))
            throw new PodiumEntityAlreadyExistException("User with given email");

        this.userRepository.save(this.convertServiceAddDtoToEntity(requestDto));
    }

    @Transactional
    public void updateUser(ProfileUpdateControllerRequest requestDto) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException {

        if(!this.isUpdateDataConsistent(requestDto))
            throw new PodiumEntityAlreadyExistException("User with given username or email");

        this.userRepository.save(this.convertProfileUpdateRequestDtoToEntity(requestDto));
    }

    @Transactional
    public void deleteUserByUsername(String username) throws PodiumEntityNotFoundException {

        if(!this.userRepository.existsByUsername(username))
            throw new PodiumEntityNotFoundException("User with given username");

        this.userRepository.deleteByUsername(username);
    }

    public User findUserByUsername(String username) throws PodiumEntityNotFoundException {

        return this.userRepository.findByUsername(username).orElseThrow(() ->

                new PodiumEntityNotFoundException("User with given username"));


    }

    public User findUserById(int id) throws PodiumEntityNotFoundException {

        return  this.userRepository.findById(id).orElseThrow(() ->

                new PodiumEntityNotFoundException("User with given id"));
    }

    public Iterable<User> findAllUsers(){
        return this.userRepository.findAll();
    }

    public Iterable<User> findAllByRole(String roleName) throws PodiumEntityNotFoundException {

        Role role = this.roleService.getEntity(roleName);

        return this.userRepository.findAllByRolesContaining(role);
    }

    public Iterable<User> findAllByCountry(String countryName) throws PodiumEntityNotFoundException {

        Country country = this.countryService.findCountryByName(countryName);

        return this.userRepository.findAllByCountry(country);
    }

    public boolean existUserByUsername(String username){
        return this.userRepository.existsByUsername(username);
    }

    public boolean existUserByEmail(String email){
        return this.userRepository.existsByEmail(email);
    }

    private boolean isUpdateDataConsistent(ProfileUpdateControllerRequest requestDto){

        User userById = this.userRepository.findById(requestDto.getId()).orElse(null);

        if (userById != null && !userById.getUsername().equals(requestDto.getUsername())
                && userById.getEmail().equals(requestDto.getEmail())) {

            return !this.userRepository.existsByUsername(requestDto.getUsername());
        }

        if (userById != null && userById.getUsername().equals(requestDto.getUsername())
                && !userById.getEmail().equals(requestDto.getEmail())) {

            return !this.userRepository.existsByEmail(requestDto.getEmail());
        }

        if (userById != null && !userById.getUsername().equals(requestDto.getUsername())
                && !userById.getEmail().equals(requestDto.getEmail())) {

            return !this.userRepository.existsByEmail(requestDto.getEmail())
                    && !this.userRepository.existsByUsername(requestDto.getUsername());

        }

        return true;

    }

    private User convertProfileUpdateRequestDtoToEntity(ProfileUpdateControllerRequest requestDto) throws PodiumEntityNotFoundException {

        User user = this.userRepository
                .findById(requestDto.getId())
                .orElseThrow(() -> new PodiumEntityNotFoundException("User with given id"));

        Country country = this.countryService.getEntity(requestDto.getCountry());

        user.setCountry(country);
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setBirthday(requestDto.getBirthday());
        user.setDescription(requestDto.getDescription());

        return user;
    }

    private FileControllerDto loadProfileImage(User user){

        if(user.getProfileImage() != null) {

            PodiumResource resource = user.getProfileImage();

            try {
                return new FileControllerDto(
                        resource.getName(),
                        resource.getType(),
                        FileCopyUtils.copyToByteArray(new File(resource.getPath()))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;

    }

    private User convertServiceAddDtoToEntity(SignUpServiceDto serviceDto) throws PodiumEntityNotFoundException {

        Country country = this.countryService.getEntity(serviceDto.getCountry());

        Role role = this.roleService.getDefaultRole();

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        return new User(
                serviceDto.getUsername(),
                serviceDto.getEmail(),
                passwordEncoder.encode(serviceDto.getPassword()),
                country,
                roles,
                serviceDto.getBirthday(),
                null,
                ""
        );
    }

    public User getEntity(String userName) throws PodiumEntityNotFoundException {

        return this.userRepository
                .findByUsername(userName)
                .orElseThrow(() -> new PodiumEntityNotFoundException("User with given username"));

    }

}
