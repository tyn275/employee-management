package com.example.employee_management.controller;

import com.example.employee_management.dto.EmployeeDto;
import com.example.employee_management.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAll(){
        return ResponseEntity.ok(employeeService.getEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> create (@RequestBody EmployeeDto employeeDto){
        EmployeeDto created = employeeService.addEmployee(employeeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
