package com.employeemanagement.employeeservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employeemanagement.employeeservice.entity.Employee;



public interface EmployeeRepository extends JpaRepository<Employee, Long>{

}
