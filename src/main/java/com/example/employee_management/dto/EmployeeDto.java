package com.example.employee_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
//   Phai co de Jackson su dung (cho phuong thuc POST)
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    private Long id;
    private String name;
    private String email;
    private Long departmentId; // For Post method
    private String departmentName; // For Get method
}
