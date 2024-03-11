package com.tadanoluka.project1.controller.controller;

import com.tadanoluka.project1.database.services.WagonService;
import com.tadanoluka.project1.security.ldap.CustomLdapUserDetailsImpl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.ldap.userdetails.LdapUserDetailsImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    WagonService wagonService;

    @Autowired
    public void setWagonRepository(WagonService wagonService) {
        this.wagonService = wagonService;
    }

    @GetMapping("hello")
    public ResponseEntity<String> handleTestAuthedRequest() {
        LdapUserDetailsImpl ldapUserDetails = (LdapUserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<String> username = ldapUserDetails.getUsername().describeConstable();
        Optional<String> dn = ldapUserDetails.getDn().describeConstable();
        Optional<String> authorities = ldapUserDetails.getAuthorities().toString().describeConstable();
        return ResponseEntity
            .ok()
            .contentType(MediaType.TEXT_PLAIN)
            .body(
                "Hello %s. \nYour dn: %s\nYour authorities: %s".formatted(
                        username.orElse("Unauthorized"),
                        dn.orElse("none"),
                        authorities.orElse("none")
                    )
            );
    }

    @GetMapping("obj")
    public ResponseEntity<String> handleTestAuthedRequest2() {
        CustomLdapUserDetailsImpl customLdapUserDetails = (CustomLdapUserDetailsImpl) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
        String ldapUserDetails = customLdapUserDetails.toString();
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(ldapUserDetails);
    }
}
