package com.podium.service;

import com.podium.dal.entity.Ban;
import com.podium.dal.repository.UserRepository;
import com.podium.service.exception.PodiumUserBannedException;
import lombok.SneakyThrows;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.podium.dal.entity.User user = this.userRepository.findByUsername(username).orElse(null);

        if (user != null) {

            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

            user.getRoles().forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole())));

            return new User(user.getUsername(), user.getPassword(),
                    grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}
