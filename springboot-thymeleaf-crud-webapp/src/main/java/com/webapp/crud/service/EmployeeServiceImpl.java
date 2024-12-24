package com.webapp.crud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.webapp.crud.model.Employee;
import com.webapp.crud.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService{
	
	@Autowired
	private EmployeeRepository employeeRepo;
	
	@Override
	public List<Employee> getAllEmployees() {
		return employeeRepo.findAll();
	}

	@Override
	public void saveEmployee(Employee employee) {
		this.employeeRepo.save(employee);
	}

	@Override
	public Employee getEmployeeById(long id) {
		Optional<Employee> optional = employeeRepo.findById(id);
		Employee employee = null;
		if(optional.isPresent()) {
			employee = optional.get();
		} else {
			throw new RuntimeException("Employee ID is not found...");
		}
		return employee;
	}

	@Override
	public void deleteEmployee(long id) {
		// TODO Auto-generated method stub
		this.employeeRepo.deleteById(id);
	}

	@Override
	public Page<Employee> findPaginated(int pageNo, int pageSize, String sortingField, String sortingDirection) {
		Sort sort = sortingDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortingField).ascending():Sort.by(sortingField).descending();
		org.springframework.data.domain.Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.employeeRepo.findAll(pageable);
	}

}
