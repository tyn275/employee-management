package com.example.employee_management.controller;

import com.example.employee_management.dto.DepartmentStatsDto;
import com.example.employee_management.dto.EmployeeDto;
import com.example.employee_management.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;  // ← Khác với @RestController!
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employees")
// De su dung thymeleaf render ra template html chu khong tra ra json
public class EmployeeViewController {

    private final EmployeeService employeeService;

    public EmployeeViewController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/list")
    public String listEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees/list";  // Tim file: templates/employees/list.html
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employeeDto", new EmployeeDto()); //Bind form
        return "employees/add";  // templates/employees/add.html
    }

    @PostMapping("/add")
    public String addEmployee(
            @Valid @ModelAttribute("employeeDto") EmployeeDto dto,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "employees/add"; // có lỗi → quay lại form, không redirect
        }

        employeeService.addEmployee(dto);
        return "redirect:/employees/list"; // thành công → chuyển về danh sách
    }

    @GetMapping("/search")
    public String searchEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String departmentName,
            Model model) {

        model.addAttribute("employees", employeeService.searchEmployees(name, departmentName));
        model.addAttribute("name", name);
        model.addAttribute("departmentName", departmentName);
        return "employees/search"; // → templates/employees/search.html
    }

    @GetMapping("/statistics")
    public String showStatistics(Model model){
//        Thong ke theo phong ban
        List<DepartmentStatsDto> deptStats = employeeService.getEmployeeCountByDepartment();
        model.addAttribute("deptStats", deptStats);

//        Tong so nhan vien
        long totalCount = employeeService.getTotalEmployeeCount();
        model.addAttribute("totalCount", totalCount);

        return "employees/statistics";
    }
}


