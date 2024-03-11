package com.tadanoluka.project1.controller.controller;

import com.tadanoluka.project1.controller.requests.SigninRequest;
import com.tadanoluka.project1.database.entity.Role;
import com.tadanoluka.project1.database.entity.User;
import com.tadanoluka.project1.database.repository.RoleRepository;
import com.tadanoluka.project1.database.repository.UserRepository;
import com.tadanoluka.project1.security.ldap.CustomLdapUserDetails;
import com.tadanoluka.project1.security.ldap.CustomLdapUserDetailsImpl;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          RoleRepository roleRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/auth/signin")
    ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest) {
        // Есть ли чел в лдапе
        Authentication auth = null;
        try {
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad credentials");
        }
        SecurityContextHolder.getContext().setAuthentication(auth);
        //

        // Вытягиваем юзер детеилс из контекста лдаповской безопасности
        CustomLdapUserDetails ldapUserDetails = (CustomLdapUserDetailsImpl) auth.getPrincipal();
        Optional<User> userOptional = userRepository.findUserByUsername(ldapUserDetails.getUsername());
        // Есть ли чел в базе
        if (userOptional.isEmpty()) {
            // Если нет - регаем в базе
            String username = ldapUserDetails.getUsername();
            String[] commonName = ldapUserDetails.getCommonName().split(" ");
            String firstname = commonName[0];
            String lastname = commonName[1];
            Set<String> grantedAuthoritiesSet = ldapUserDetails
                .getAuthorities()
                .stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority().toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());
            String role = "user";
            if (grantedAuthoritiesSet.contains("role_admins")) role = "admin";

            Role userRole = roleRepository.findRoleByNameIgnoreCase(role).orElseThrow();
            User user = new User(username, firstname, lastname, userRole);
            userRepository.save(user);
        }

        // Возвращаем что всё ок
        return ResponseEntity.ok().body("");
    }
}
