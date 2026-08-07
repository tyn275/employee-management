package com.example.employee_management.controller;

import com.example.employee_management.service.UtilityService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//Dung @RestController thay cho @Controller de tra ve du lieu Json thay vi render ra HTML
@RestController
@RequestMapping("/api/public")
public class PublicController {

    private final UtilityService utilityService;

    public PublicController(UtilityService utilityService) {
        this.utilityService = utilityService;
    }

//    Module 1
    @GetMapping("/hello")
    public String hello(){
        return "Hello, Spring Boot!";
    }

//    Module 2
    @GetMapping("/generate-code")
    public String generateCode(){
        return utilityService.generateEmployeeCode();
    }

    @GetMapping("/format-name")
    public String formatName(@RequestParam String name){
        return utilityService.formatName(name);
    }
}
