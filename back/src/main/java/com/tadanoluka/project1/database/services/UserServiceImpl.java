package com.tadanoluka.project1.database.services;

import com.tadanoluka.project1.database.entity.User;
import com.tadanoluka.project1.database.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findUserByUsername(String name) {
        return userRepository.findUserByUsername(name);
    }
}
