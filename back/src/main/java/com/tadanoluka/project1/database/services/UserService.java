package com.tadanoluka.project1.database.services;

import com.tadanoluka.project1.database.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByUsername(String name);
}
