package com.takehome.stayease.security.service;

import com.takehome.stayease.entity.User;
import com.takehome.stayease.repo.UserRepository;
import com.takehome.stayease.security.impl.UserDetailsImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> foundUserOptional = userRepository.findByEmail(username);

        if (foundUserOptional.isEmpty()) throw new UsernameNotFoundException("User not found");

        User foundUser = foundUserOptional.get();

        return new UserDetailsImpl(foundUser.getRole().toString(), foundUser.getPassword(), foundUser.getEmail());
    }
}
