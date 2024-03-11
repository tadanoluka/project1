package com.tadanoluka.project1.security.ldap;

import org.springframework.security.ldap.userdetails.LdapUserDetails;

public interface CustomLdapUserDetails extends LdapUserDetails {
    String getCommonName();
}
