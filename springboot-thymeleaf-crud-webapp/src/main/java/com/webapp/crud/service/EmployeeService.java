package com.webapp.crud.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.webapp.crud.model.Employee;

public interface EmployeeService {
	List<Employee> getAllEmployees();
	void saveEmployee(Employee employee);
	Employee getEmployeeById(long id);
	void deleteEmployee(long id);
	Page<Employee> findPaginated(int pageNo, int pageSize, String sortingField, String sortingDirection);
}
