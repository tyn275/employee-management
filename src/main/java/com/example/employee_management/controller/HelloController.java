package com.example.employee_management.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//Dung @RestController thay cho @Controller de tra ve du lieu Json thay vi render ra HTML
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "Hello, Spring Boot!";
    }
}
