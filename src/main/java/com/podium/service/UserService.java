package com.podium.service;

import com.podium.model.Country;
import com.podium.model.Role;
import com.podium.model.SignUpRequest;
import com.podium.model.User;
import com.podium.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(SignUpRequest signUpRequest){

        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());

        Country country = new Country();
        country.setName(signUpRequest.getCountry());

        user.setCountry(country);

        Role role = new Role();
        role.setName("subscriber");

        user.getRoles().add(role);
        user.setBirthday(signUpRequest.getBirthday());

        user.setProfileImage(null);

        this.userRepository.save(user);


    }


}
