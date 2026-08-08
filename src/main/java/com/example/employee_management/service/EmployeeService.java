package com.example.employee_management.service;

import com.example.employee_management.dto.EmployeeDto;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class EmployeeService {

    @Getter
    private final List<EmployeeDto> employees = new ArrayList<>();

    private final AtomicLong idCounter = new AtomicLong(1);

    public EmployeeDto addEmployee(EmployeeDto employeeDto){
        employeeDto.setId(idCounter.getAndIncrement());
        employees.add(employeeDto);
        return employeeDto;
    }

}
