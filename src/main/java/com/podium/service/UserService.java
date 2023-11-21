package com.podium.service;

import com.podium.dal.entity.Country;
import com.podium.dal.entity.PodiumResource;
import com.podium.dal.entity.Role;
import com.podium.dal.entity.User;
import com.podium.controller.dto.other.FileControllerDto;
import com.podium.dal.repository.CountryRepository;
import com.podium.dal.repository.RoleRepository;
import com.podium.dal.repository.UserRepository;
import com.podium.service.dto.request.ProfileUpdateServiceRequest;
import com.podium.service.dto.request.SignUpServiceRequest;
import com.podium.service.exception.PodiumAuthorityException;
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
    private ResourceService resourceService;
    private SecurityService securityService;

    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, RoleService roleService, CountryRepository countryRepository, CountryService countryService, ResourceService resourceService, SecurityService securityService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.countryService = countryService;
        this.resourceService = resourceService;
        this.securityService = securityService;
    }

    @Transactional
    public void addUser(SignUpServiceRequest requestDto) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException {

        if(this.userRepository.existsByUsername(requestDto.getUsername()))
            throw new PodiumEntityAlreadyExistException("User with given username");

        if(this.userRepository.existsByEmail(requestDto.getEmail()))
            throw new PodiumEntityAlreadyExistException("User with given email");

        this.userRepository.save(this.convertServiceAddDtoToEntity(requestDto));
    }

    @Transactional
    public void updateUser(ProfileUpdateServiceRequest requestDto, String username) throws PodiumEntityAlreadyExistException, PodiumEntityNotFoundException, PodiumAuthorityException {

        User user = this.getEntity(username);
        this.securityService.validateUserSubscriberAuthority(user);

        if(!this.isUpdateDataConsistent(requestDto))
            throw new PodiumEntityAlreadyExistException("User with given username or email");

        this.userRepository.save(this.convertProfileUpdateRequestDtoToEntity(requestDto));
    }

    @Transactional
    public void deleteUserByUsername(String username,String adminUsername) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User admin = this.getEntity(adminUsername);
        this.securityService.validateUserAdminAuthority(admin);

        if(!this.userRepository.existsByUsername(username))
            throw new PodiumEntityNotFoundException("User with given username");

        this.userRepository.deleteByUsername(username);
    }

    public User findUserByUsername(String usernameToFind, String authorUsername) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User user = this.userRepository.findByUsername(usernameToFind).orElseThrow(() ->
                new PodiumEntityNotFoundException("User with given username"));

        User author = this.userRepository.findByUsername(authorUsername).orElseThrow(() ->
                new PodiumEntityNotFoundException("User with given username"));

        if(!(this.securityService.isUserAdminOrModerator(author) || user.getUsername().equals(authorUsername)))
            throw new PodiumAuthorityException("admin or moderator");

        return user;

    }

    public Iterable<User> findAllUsers(String username) throws PodiumEntityNotFoundException, PodiumAuthorityException {

        User user = this.getEntity(username);

        if(!(this.securityService.isUserAdminOrModerator(user) || user.getUsername().equals(username)))
            throw new PodiumAuthorityException("admin or moderator");

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

    private boolean isUpdateDataConsistent(ProfileUpdateServiceRequest requestDto){

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

    private User convertProfileUpdateRequestDtoToEntity(ProfileUpdateServiceRequest requestDto) throws PodiumEntityNotFoundException {

        User user = this.userRepository
                .findById(requestDto.getId())
                .orElseThrow(() -> new PodiumEntityNotFoundException("User with given id"));

        PodiumResource imageResource = this.resourceService.createPodiumImageResource(requestDto.getImage());

        user.setCountry(this.countryService.getEntity(requestDto.getCountry()));
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setPassword(this.getUpdatedPassword(user.getPassword(),requestDto.getPassword()));
        user.setBirthday(requestDto.getBirthday());
        user.setDescription(requestDto.getDescription());
        user.setProfileImage(imageResource);

        return user;
    }

    private String getUpdatedPassword(String currentPassword,String requestPassword){

        if(passwordEncoder
                .encode(requestPassword)
                .equals(currentPassword))
            System.out.println("ROWNA SIE");

        System.out.println("REQUEST: " + requestPassword);
        System.out.println("CURRENT : " + currentPassword);


        return requestPassword.equals(currentPassword)
                ? currentPassword
                : passwordEncoder.encode(requestPassword);
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

    private User convertServiceAddDtoToEntity(SignUpServiceRequest serviceDto) throws PodiumEntityNotFoundException {

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
