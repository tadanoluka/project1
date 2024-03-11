package com.tadanoluka.project1.controller.controller;

import com.tadanoluka.project1.database.entity.User;
import com.tadanoluka.project1.database.repository.UserRepository;
import com.tadanoluka.project1.dto.UserDTO;
import com.tadanoluka.project1.security.ldap.CustomLdapUserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ProfileController {

    private final UserRepository userRepository;

    @Autowired
    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/user")
    ResponseEntity<?> getUser() {

        CustomLdapUserDetailsImpl ldapUserDetails = (CustomLdapUserDetailsImpl) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();

        Optional<UserDTO> user = userRepository.findUserByUsername(ldapUserDetails.getUsername()).map(UserDTO::new);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Try to re-login");
        } else {
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(user.get());
        }



    }
}
