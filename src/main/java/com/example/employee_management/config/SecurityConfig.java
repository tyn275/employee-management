package com.example.employee_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        http
//                Tam thoi tat de su dung phuong thuc POST
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**", "/api/auth/**").permitAll()
//                        Tam thoi set permitAll()
                        .requestMatchers("/api/v1/employees/**").permitAll()
//                        . requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated());
        return http.build();
    }
}
