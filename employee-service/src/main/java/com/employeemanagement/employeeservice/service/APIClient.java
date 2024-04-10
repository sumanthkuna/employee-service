package com.employeemanagement.employeeservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.employeemanagement.employeeservice.dto.DepartmentDto;

@FeignClient(name = "DEPARTMENT-SERVICE")
//instead of urls we are passing the name because spring cloud will take care of load balancing when there are multiple instances and some instances are down
public interface APIClient {

	@GetMapping("api/departments/{department-code}")
	DepartmentDto getDepartmentByCode(@PathVariable("department-code") String departmentCode);
}
