package com.employeemanagement.employeeservice.service;

import com.employeemanagement.employeeservice.dto.APIResponseDto;
import com.employeemanagement.employeeservice.dto.EmployeeDto;

public interface EmployeeService {
	
	EmployeeDto saveEmployee(EmployeeDto employeeDto);
	
	APIResponseDto getEmployeeById(Long id);

}
