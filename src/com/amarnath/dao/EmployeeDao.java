package com.amarnath.dao;

import java.util.List;

import com.amarnath.model.Employee;

public interface EmployeeDao {
	// save employee
	boolean saveEmployee(Employee employee);
		
	// delete employee
	boolean deleteEmployee(int id);
		
	// update Employee
	boolean updateEmployee(Employee employee);
		
	// read employee
	Employee getEmployee(int id);
		
	// get all employees
	List<Employee> getAllEmployees();
}
