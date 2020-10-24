package com.podium.service;

import com.podium.model.dto.response.CountryResponseDto;
import com.podium.model.dto.response.UserResponseDto;
import com.podium.model.entity.Country;
import com.podium.model.entity.Role;
import com.podium.model.dto.request.SignUpRequestDto;
import com.podium.model.entity.User;
import com.podium.repository.CountryRepository;
import com.podium.repository.RoleRepository;
import com.podium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    private RoleRepository roleRepository;

    private CountryRepository countryRepository;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository, CountryRepository countryRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.countryRepository = countryRepository;
    }

    public void addUser(SignUpRequestDto signUpRequestDto){
        this.userRepository.save(this.convertRequestDtoToEntity(signUpRequestDto));
    }

    public UserResponseDto findUserByUsername(String username){
       return this.convertEntityToResponseDto(
               this.userRepository.findByUsername(username));
    }

    public Iterable<UserResponseDto> findAllUsers(){

        List<UserResponseDto> responseDtos = new ArrayList<>();

        for(User user : this.userRepository.findAll())
            responseDtos.add(this.convertEntityToResponseDto(user));

        return responseDtos;
    }

    public Iterable<UserResponseDto> findAllByRole(String roleName){

        List<UserResponseDto> responseDtos = new ArrayList<>();

        Role role = this.roleRepository.findByRole(roleName);

        for(User user : this.userRepository.findAllByRolesContaining(role))
            responseDtos.add(this.convertEntityToResponseDto(user));

        return responseDtos;
    }

    public Iterable<UserResponseDto> findAllByCountry(String countryName){

        List<UserResponseDto> responseDtos = new ArrayList<>();

        Country country = this.countryRepository.findByName(countryName);

        for(User user : this.userRepository.findAllByCountry(country))
            responseDtos.add(this.convertEntityToResponseDto(user));

        return responseDtos;
    }

    public boolean existUserByUsername(String username){
        return this.userRepository.existsByUsername(username);
    }

    public boolean existUserByEmail(String email){
        return this.userRepository.existsByEmail(email);
    }

    public void updateUserUsername(String presentUsername, String newUsername){
        this.userRepository.updateUserUsername(presentUsername,newUsername);
    }

    public void deleteUserByUsername(String username){

        this.userRepository.deleteByUsername(username);
    }

    private User convertRequestDtoToEntity(SignUpRequestDto requestDto){

        User user = new User();
        user.setUsername(requestDto.getUsername());
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        Country country = countryRepository.findByName(requestDto.getCountry());
        user.setCountry(country);

        Role role = this.roleRepository.findByRole("subscriber");

        if(role == null){
            role = new Role();
            role.setRole("subscriber");
            this.roleRepository.save(role);
        }

        user.getRoles().add(role);
        user.setBirthday(requestDto.getBirthday());

        return user;
    }

    private UserResponseDto convertEntityToResponseDto(User user){

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(user.getUserId());
        responseDto.setUsername(user.getUsername());
        responseDto.setEmail(user.getEmail());
        responseDto.setPassword(user.getPassword());
        responseDto.setCountry(user.getCountry().getPrintable_name());
        Set<String> roles = user.getRoles().stream().map(Role::getRole).collect(Collectors.toSet());
        responseDto.setRoles(roles);
        responseDto.setBirthday(user.getBirthday());
        return responseDto;

    }
}
