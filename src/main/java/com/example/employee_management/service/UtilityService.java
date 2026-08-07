package com.example.employee_management.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UtilityService {

//    Tao ma nhan vien tu dong
    public String generateEmployeeCode(){
//      Dang ma nhan vien:  EMP-<6 ky tu dau cua uuid>
        String uuidPart = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "EMP-" + uuidPart;
    }

//    Chuan hoa ten
    public String formatName(String rawName){
        String [] words = rawName.trim().toLowerCase().split("\\s+");
        StringBuilder result = new StringBuilder();
        for(String word: words){
            if (!word.isEmpty()){
                result.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return result.toString().trim();
    }
}
