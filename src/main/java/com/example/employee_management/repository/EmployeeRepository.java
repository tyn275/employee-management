package com.example.employee_management.repository;

import com.example.employee_management.dto.DepartmentStatsDto;
import com.example.employee_management.entity.Employee;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

//  Tim kiem theo ten
  List<Employee> findByNameContainingIgnoreCase(String name);

//  Tim kiem theo phong ban
  List<Employee>findByDepartment_NameContainingIgnoreCase(String departmentName);

//  Tim kiem theo ten va phong ban
  List<Employee>findByNameContainingIgnoreCaseAndDepartment_NameContainingIgnoreCase(String name, String departmentName);

//  Thong ke so nhan vien theo tung phong ban
  @Query("SELECT new com.example.employee_management.dto.DepartmentStatsDto(d.name, COUNT(e)) " +
          "FROM Employee e JOIN e.department d " +
          "GROUP BY d.name " +
          "ORDER BY COUNT(e) DESC")
  List<DepartmentStatsDto> countEmployeesByDepartment();
}
