package com.podium.service;

import com.podium.model.entity.Country;
import com.podium.model.entity.Role;
import com.podium.model.request.SignUpRequest;
import com.podium.model.entity.User;
import com.podium.repository.CountryRepository;
import com.podium.repository.RoleRepository;
import com.podium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public void addUser(SignUpRequest signUpRequest){

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());

        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        Country country = countryRepository.findByName(signUpRequest.getCountry());
        country.setName(signUpRequest.getCountry());

        user.setCountry(country);

        Role role = this.roleRepository.findByRole("subscriber");

        user.getRoles().add(role);
        user.setBirthday(signUpRequest.getBirthday());

        user.setProfileImage(null);

        this.userRepository.save(user);


    }

    public User findUserByUsername(String username){
       return this.userRepository.findByUsername(username);
    }

    public Iterable<User> findAllUsers(){
        System.out.println("Find all");
        return this.userRepository.findAll();
    }

    public Iterable<User> findAllByRole(String roleName){

        Role role = this.roleRepository.findByRole(roleName);

        return this.userRepository.findAllByRolesContaining(role);
    }

    public Iterable<User> findAllByCountry(String countryName){

        Country country = this.countryRepository.findByName(countryName);

        return this.userRepository.findAllByCountry(country);

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
}
