package com.example.employee_management.controller;

import com.example.employee_management.dto.AuthResponeDto;
import com.example.employee_management.dto.LoginDto;
import com.example.employee_management.dto.RegisterDto;
import com.example.employee_management.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponeDto> register(@RequestBody RegisterDto request){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponeDto> login(@RequestBody LoginDto request){
        return ResponseEntity.ok(authService.login(request));
    }
}
