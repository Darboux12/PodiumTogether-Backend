package com.podium.service;

import com.podium.model.dto.request.ProfileUpdateRequestDto;
import com.podium.model.dto.response.UserResponseDto;
import com.podium.model.entity.localization.Country;
import com.podium.model.entity.resource.PodiumResource;
import com.podium.model.entity.user.Role;
import com.podium.model.dto.request.SignUpRequestDto;
import com.podium.model.entity.user.User;
import com.podium.model.dto.other.PodiumFile;
import com.podium.repository.CountryRepository;
import com.podium.repository.RoleRepository;
import com.podium.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

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

    public void addUser(SignUpRequestDto signUpRequestDto){
        this.userRepository.save(this.convertSignUpRequestDtoToEntity(signUpRequestDto));
    }

    public UserResponseDto findUserByUsername(String username){
       return this.convertEntityToResponseDto(
               Objects.requireNonNull(this.userRepository.findByUsername(username).orElse(null)));
    }

    public Iterable<UserResponseDto> findAllUsers(){

        List<UserResponseDto> responseDtos = new ArrayList<>();

        this.userRepository
                .findAll()
                .forEach(x -> responseDtos
                        .add(this.convertEntityToResponseDto(x))
                );

        return responseDtos;
    }

    public Iterable<UserResponseDto> findAllByRole(String roleName){

        List<UserResponseDto> responseDtos = new ArrayList<>();

        Role role = this.roleRepository.findByRole(roleName);

        this.userRepository
                .findAllByRolesContaining(role)
                .forEach(x -> responseDtos
                        .add(this.convertEntityToResponseDto(x))
                );

        return responseDtos;
    }

    public Iterable<UserResponseDto> findAllByCountry(String countryName){

        List<UserResponseDto> responseDtos = new ArrayList<>();

        Country country = this.countryRepository.findByName(countryName);

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

    public void updateUser(ProfileUpdateRequestDto requestDto){

        this.userRepository.save(
                this.convertProfileUpdateRequestDtoToEntity(requestDto));
    }

    public boolean isUpdateDataConsistent(ProfileUpdateRequestDto requestDto){

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

    public void deleteUserByUsername(String username){
        this.userRepository.deleteByUsername(username);
    }

    private User convertProfileUpdateRequestDtoToEntity(ProfileUpdateRequestDto requestDto){

        User user = this.userRepository.findById(requestDto.getId()).orElse(null);

        if (user != null) {

            user.setUsername(requestDto.getUsername());
            Country country = this.countryRepository.findByName(requestDto.getCountry());
            user.setCountry(country);
            user.setEmail(requestDto.getEmail());
            user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
            user.setBirthday(requestDto.getBirthday());
            user.setDescription(requestDto.getDescription());

            return user;

        }

        return null;
    }

    private User convertSignUpRequestDtoToEntity(SignUpRequestDto requestDto){

        Country country = countryRepository.findByName(requestDto.getCountry());
        Role role = this.roleRepository.findByRole("subscriber");

        if(role == null){
            role = new Role();
            role.setRole("subscriber");
            this.roleRepository.save(role);
        }

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        return new User(
                requestDto.getUsername(),
                requestDto.getEmail(),
                passwordEncoder.encode(requestDto.getPassword()),
                country,
                roles,
                requestDto.getBirthday(),
                null,
                ""
        );
    }

    private UserResponseDto convertEntityToResponseDto(User user){

        try {
            return new UserResponseDto(
                    user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getCountry().getPrintableName(),
                    user.getRoles().stream().map(Role::getRole).collect(Collectors.toSet()),
                    user.getBirthday(),
                    this.loadProfileImage(user),
                    null,
                    null,
                    user.getDescription()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    private PodiumFile loadProfileImage(User user) throws IOException {

        if(user.getProfileImage() != null) {

            PodiumResource resource = user.getProfileImage();

            return new PodiumFile(
                    resource.getName(),
                    resource.getType(),
                    FileCopyUtils.copyToByteArray(new File(resource.getPath()))
            );
        }

        return null;

    }
}
