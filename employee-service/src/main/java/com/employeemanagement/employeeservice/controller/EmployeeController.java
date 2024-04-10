package com.employeemanagement.employeeservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeemanagement.employeeservice.dto.APIResponseDto;
import com.employeemanagement.employeeservice.dto.EmployeeDto;
import com.employeemanagement.employeeservice.service.EmployeeService;

@RestController
@RequestMapping("api/employees")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	
	@PostMapping
	public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
		
		EmployeeDto savedEmployeeDto = employeeService.saveEmployee(employeeDto);
		
		return new ResponseEntity<EmployeeDto>(savedEmployeeDto, HttpStatus.CREATED);
	}
	
	//Build get employee by id method
	
	@GetMapping("{id}")
	public ResponseEntity<APIResponseDto> getEmployeeById(@PathVariable("id") Long employeeId){
		
		APIResponseDto apiResponseDto = employeeService.getEmployeeById(employeeId);
		
		return new ResponseEntity<APIResponseDto>(apiResponseDto, HttpStatus.OK);
	}
}
