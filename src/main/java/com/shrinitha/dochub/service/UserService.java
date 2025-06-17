package com.shrinitha.dochub.service;

import com.shrinitha.dochub.model.User;
import com.shrinitha.dochub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
}
