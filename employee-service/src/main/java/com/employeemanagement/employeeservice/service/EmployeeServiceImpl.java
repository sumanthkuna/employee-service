package com.employeemanagement.employeeservice.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.employeemanagement.employeeservice.dto.APIResponseDto;
import com.employeemanagement.employeeservice.dto.DepartmentDto;
import com.employeemanagement.employeeservice.dto.EmployeeDto;
import com.employeemanagement.employeeservice.entity.Employee;
import com.employeemanagement.employeeservice.exception.ResourceNotFoundException;
import com.employeemanagement.employeeservice.repository.EmployeeRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;


@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	EmployeeRepository employeeRepository;
	
//	@Autowired
//	RestTemplate restTemplate;

	@Autowired
	APIClient apiClient;
	
	
	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
		
		ModelMapper modelMapper = new ModelMapper();
		Employee employeeToBeSaved = modelMapper.map(employeeDto,Employee.class);
		Employee savedEmployee = employeeRepository.save(employeeToBeSaved);
		EmployeeDto savedEmployeeDto = modelMapper.map(savedEmployee,EmployeeDto.class);
		
		return savedEmployeeDto;
	}

	@Override
	@CircuitBreaker(name ="${spring.application.name}", fallbackMethod = "fallbackMethod")
	public APIResponseDto getEmployeeById(Long id) {
		
		Employee employeeWithGivenId = employeeRepository.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Employee", "id", id)
				);
		ModelMapper modelMapper = new ModelMapper();
		EmployeeDto employeeWithGivenIdDto = modelMapper.map(employeeWithGivenId,EmployeeDto.class);
		
		//call get department by department code api
		
		//ResponseEntity<DepartmentDto> responseEntity= restTemplate.getForEntity("http://localhost:8080/api/departments/"+ employeeWithGivenIdDto.getDepartmentCode(), DepartmentDto.class);
		
		//using spring cloud openfeign to communicate with a Department endpoint
		
		DepartmentDto departmentDto = apiClient.getDepartmentByCode(employeeWithGivenIdDto.getDepartmentCode());
		
		APIResponseDto apiResponseDto = new APIResponseDto();
		apiResponseDto.setEmployeeDto(employeeWithGivenIdDto);
		apiResponseDto.setDepartmentDto(departmentDto);
		
		return apiResponseDto;
	}
	
	
	public APIResponseDto fallbackMethod(Long id,Exception e) {
		
		Employee employeeWithGivenId = employeeRepository.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Employee", "id", id)
				);
		ModelMapper modelMapper = new ModelMapper();
		EmployeeDto employeeWithGivenIdDto = modelMapper.map(employeeWithGivenId,EmployeeDto.class);
		
		//setting dummy department dto
		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setDepartmentCode("RD001");
		departmentDto.setDepartmentName("R & D");
		departmentDto.setDepartmentDescription("Research and Development");
		
		APIResponseDto apiResponseDto = new APIResponseDto();
		apiResponseDto.setEmployeeDto(employeeWithGivenIdDto);
		apiResponseDto.setDepartmentDto(departmentDto);
		
		return apiResponseDto;
    }

}
