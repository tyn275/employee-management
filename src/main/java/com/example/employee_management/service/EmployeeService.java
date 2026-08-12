package com.example.employee_management.service;

import com.example.employee_management.dto.EmployeeDto;
import com.example.employee_management.entity.Department;
import com.example.employee_management.entity.Employee;
import com.example.employee_management.exception.EmployeeNotFoundException;
import com.example.employee_management.repository.DepartmentRepository;
import com.example.employee_management.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

//    Lay toan bo danh sach Employee
    public List<EmployeeDto> getAllEmployees(){
        logger.debug("Đang lấy danh sách toàn bộ nhân viên");
        return employeeRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public EmployeeDto addEmployee(EmployeeDto dto) {
        Employee employee = toEntity(dto);
        Employee saved = employeeRepository.save(employee);
        logger.info("Đã thêm nhân viên mới: id={}, name={}", saved.getId(), saved.getName());
        return toDto(saved);
    }

    public EmployeeDto updateEmployee(Long id, EmployeeDto dto){
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException("Không tìm thấy nhân viên với id = " + id));

        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());

        if(dto.getDepartmentId() != null){
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> new EmployeeNotFoundException("Không tìm thấy phòng ban với id = " + dto.getDepartmentId()));
            employee.setDepartment(department);
        }
        return toDto(employeeRepository.save(employee));
    }
    public void deleteEmployee(Long id){
        if(!employeeRepository.existsById(id)){
            throw new EmployeeNotFoundException("Không tìm thấy nhân viên với id = " + id);
        }
        employeeRepository.deleteById(id);
    }

    public Optional<EmployeeDto> getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(this::toDto);
    }

    private EmployeeDto toDto(Employee employee) {
        EmployeeDto dto = modelMapper.map(employee, EmployeeDto.class);
        if (employee.getDepartment() != null) {
            dto.setDepartmentId(employee.getDepartment().getId());
            dto.setDepartmentName(employee.getDepartment().getName());
        }
        return dto;
    }

    public List<EmployeeDto> searchEmployees(String name, String departmentName) {
        List<Employee> results;

        boolean hasName = name != null && !name.isBlank();
        boolean hasDept = departmentName != null && !departmentName.isBlank();

        if (hasName && hasDept) {
            results = employeeRepository
                    .findByNameContainingIgnoreCaseAndDepartment_NameContainingIgnoreCase(name, departmentName);
        } else if (hasName) {
            results = employeeRepository.findByNameContainingIgnoreCase(name);
        } else if (hasDept) {
            results = employeeRepository.findByDepartment_NameContainingIgnoreCase(departmentName);
        } else {
            results = employeeRepository.findAll();
        }

        return results.stream().map(this::toDto).collect(Collectors.toList());
    }

    private Employee toEntity(EmployeeDto dto) {
        Employee employee = modelMapper.map(dto, Employee.class);
        employee.setId(null); // đảm bảo luôn tạo mới, tránh ghi đè nhầm nếu client lỡ gửi id

        if (dto.getDepartmentId() != null) {
            Department department = departmentRepository.findById(dto.getDepartmentId())
                    .orElseThrow(() -> {
                        logger.warn("Không tìm thấy phòng ban với id={}", dto.getDepartmentId());
                        return new EmployeeNotFoundException(
                                "Không tìm thấy phòng ban với id = " + dto.getDepartmentId());
                    });
            employee.setDepartment(department);
        }
        return employee;
    }

    @Cacheable("employeeCount")
    public long getTotalEmployeeCount() {
        logger.info("Đang query DB để đếm tổng số nhân viên (cache miss)");
        return employeeRepository.count();
    }
}

