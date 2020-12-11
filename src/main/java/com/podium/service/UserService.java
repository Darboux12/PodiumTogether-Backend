package com.podium.service;

import com.podium.controller.dto.request.ProfileUpdateRequest;
import com.podium.controller.dto.response.UserResponse;
import com.podium.dal.entity.Country;
import com.podium.dal.entity.PodiumResource;
import com.podium.dal.entity.Role;
import com.podium.dal.entity.User;
import com.podium.controller.dto.other.PodiumFileDto;
import com.podium.dal.repository.CountryRepository;
import com.podium.dal.repository.RoleRepository;
import com.podium.dal.repository.UserRepository;
import com.podium.service.dto.SignUpServiceDto;
import com.podium.service.exception.PodiumEntityAlreadyExistException;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private CountryRepository countryRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, CountryRepository countryRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.countryRepository = countryRepository;
    }

    @Transactional
    public void addUser(SignUpServiceDto requestDto){

        if(this.userRepository.existsByUsername(requestDto.getUsername()))
            throw new PodiumEntityAlreadyExistException("User with given username");

        if(this.userRepository.existsByEmail(requestDto.getEmail()))
            throw new PodiumEntityAlreadyExistException("User with given email");

        if(!this.countryRepository.existsByName(requestDto.getCountry()))
            throw new PodiumEntityNotFoundException("Country");

        this.userRepository.save(this.convertSignUpDtoToEntity(requestDto));
    }

    @Transactional
    public void updateUser(ProfileUpdateRequest requestDto){

        if(!this.isUpdateDataConsistent(requestDto))
            throw new PodiumEntityAlreadyExistException("User with given username or email");

        if(!this.countryRepository.existsByName(requestDto.getCountry()))
            throw new PodiumEntityNotFoundException("Country");

        this.userRepository.save(this.convertProfileUpdateRequestDtoToEntity(requestDto));
    }

    @Transactional
    public void deleteUserByUsername(String username){

        if(!this.userRepository.existsByUsername(username))
            throw new PodiumEntityNotFoundException("User with given username");

        this.userRepository.deleteByUsername(username);
    }

    public UserResponse findUserByUsername(String username){

        User user = this.userRepository.findByUsername(username).orElseThrow(() ->

                new PodiumEntityNotFoundException("User with given username"));

       return this.convertEntityToResponseDto(user);
    }

    public UserResponse findUserById(int id){

        User user = this.userRepository.findById(id).orElseThrow(() ->

                new PodiumEntityNotFoundException("User with given id"));

        return this.convertEntityToResponseDto(user);
    }

    public Iterable<UserResponse> findAllUsers(){

        List<UserResponse> responseDtos = new ArrayList<>();

        this.userRepository
                .findAll()
                .forEach(x -> responseDtos
                        .add(this.convertEntityToResponseDto(x))
                );

        return responseDtos;
    }

    public Iterable<UserResponse> findAllByRole(String roleName){

        List<UserResponse> responseDtos = new ArrayList<>();

        Role role = this.roleRepository
                .findByRole("subscriber")
                .orElseThrow(() -> new PodiumEntityNotFoundException("Role"));

        this.userRepository
                .findAllByRolesContaining(role)
                .forEach(x -> responseDtos
                        .add(this.convertEntityToResponseDto(x))
                );

        return responseDtos;
    }

    public Iterable<UserResponse> findAllByCountry(String countryName){

        List<UserResponse> responseDtos = new ArrayList<>();

        Country country =
                this.countryRepository
                        .findByName(countryName)
                        .orElseThrow(() -> new PodiumEntityNotFoundException("Country"));

        this.userRepository
                .findAllByCountry(country)
                .forEach(x -> responseDtos
                        .add(this.convertEntityToResponseDto(x))
                );

        return responseDtos;
    }

    public boolean existUserByUsername(String username){
        return this.userRepository.existsByUsername(username);
    }

    public boolean existUserByEmail(String email){
        return this.userRepository.existsByEmail(email);
    }

    private boolean isUpdateDataConsistent(ProfileUpdateRequest requestDto){

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

    private User convertProfileUpdateRequestDtoToEntity(ProfileUpdateRequest requestDto){

        User user = this.userRepository
                .findById(requestDto.getId())
                .orElseThrow(() -> new PodiumEntityNotFoundException("User with given id"));

        Country country =
                this.countryRepository
                        .findByName(requestDto.getCountry())
                        .orElseThrow(() -> new PodiumEntityNotFoundException("Country"));

        user.setCountry(country);
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setBirthday(requestDto.getBirthday());
        user.setDescription(requestDto.getDescription());

        return user;
    }

    private User convertSignUpDtoToEntity(SignUpServiceDto signUpServiceDto){

        Country country =
                this.countryRepository
                        .findByName(signUpServiceDto.getCountry())
                        .orElseThrow(() -> new PodiumEntityNotFoundException("Country"));

        Role role = this.roleRepository
                .findByRole("subscriber")
                .orElseThrow(() -> new PodiumEntityNotFoundException("Role"));

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        return new User(
                signUpServiceDto.getUsername(),
                signUpServiceDto.getEmail(),
                passwordEncoder.encode(signUpServiceDto.getPassword()),
                country,
                roles,
                signUpServiceDto.getBirthday(),
                null,
                ""
        );
    }

    private UserResponse convertEntityToResponseDto(User user){

            return new UserResponse(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getCountry().getPrintableName(),
                    user.getRoles().stream().map(Role::getRole).collect(Collectors.toSet()),
                    user.getBirthday(),
                    this.loadProfileImage(user),
                    user.getEventsJoined(),
                    user.getEventsCreated(),
                    user.getDescription()
            );

    }

    private PodiumFileDto loadProfileImage(User user){

        if(user.getProfileImage() != null) {

            PodiumResource resource = user.getProfileImage();

            try {
                return new PodiumFileDto(
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

}
