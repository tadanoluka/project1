package com.tadanoluka.project1.security;

import com.tadanoluka.project1.security.ldap.CustomLdapUserDetailsMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    CustomLdapUserDetailsMapper customLdapUserDetailsMapper;
    Custom401EntryPoint custom401EntryPoint;

    @Autowired
    public void setCustomLdapUserDetailsMapper(CustomLdapUserDetailsMapper customLdapUserDetailsMapper) {
        this.customLdapUserDetailsMapper = customLdapUserDetailsMapper;
    }

    @Autowired
    public void setAuthenticationEntryPoint(Custom401EntryPoint custom401EntryPoint) {
        this.custom401EntryPoint = custom401EntryPoint;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    @Primary
    public AuthenticationManagerBuilder configureAuthenticationManagerBuilder(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .ldapAuthentication()
            .userDnPatterns("uid={0},ou=people")
            .groupSearchBase("ou=groups")
            .contextSource()
            .url("ldap://localhost:8389/dc=springframework,dc=org")
            .and()
            .passwordCompare()
            .passwordEncoder(new BCryptPasswordEncoder())
            .passwordAttribute("userPassword")
            .and()
            .userDetailsContextMapper(customLdapUserDetailsMapper);
        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .httpBasic(httpBasic -> {
                httpBasic.authenticationEntryPoint(custom401EntryPoint);
            })
            .csrf(AbstractHttpConfigurer::disable)
            .cors(corsConfig ->
                corsConfig.configurationSource(request -> {
                    CorsConfiguration cors = new CorsConfiguration().applyPermitDefaultValues();
                    cors.setAllowedOriginPatterns(List.of("*", "**"));
                    cors.addAllowedMethod(HttpMethod.PATCH);
                    return cors;
                })
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(request -> request
                    .requestMatchers("/api/v1/auth/signin").permitAll()
                    .anyRequest().authenticated());

        return http.build();
    }
}
