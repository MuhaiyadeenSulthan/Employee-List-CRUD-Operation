package com.webapp.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.webapp.crud.model.Employee;
import com.webapp.crud.service.EmployeeService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	private static final int PAGE_SIZE = 6; // Number of employees displayed per page

	// Display the home page with a list of employees
	@GetMapping
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);
	}

	// Show form to add a new employee
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "addNewEmployee";
	}

	// Save employee to the database
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		employeeService.saveEmployee(employee);
		return "redirect:/employees"; // Redirect to the employee list
	}

	// Show form to update an existing employee
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable long id, Model model) {
		Employee employee = employeeService.getEmployeeById(id);
		if (employee == null) {
			// Handle case where employee is not found
			throw new IllegalArgumentException("Invalid employee ID: " + id);
		}
		model.addAttribute("employee", employee);
		return "updateEmployeeForm";
	}

	// Delete an employee by ID
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployee(@PathVariable(value = "id") long id) {
		employeeService.deleteEmployee(id);
		return "redirect:/employees";
	}

	// Handle pagination and sorting
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
			@RequestParam(value = "sortingField", defaultValue = "firstName") String sortingField,
			@RequestParam(value = "sortingDirection", defaultValue = "asc") String sortingDirection, Model model) {

		Page<Employee> page = employeeService.findPaginated(pageNo, PAGE_SIZE, sortingField, sortingDirection);
		List<Employee> listEmployees = page.getContent();

		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		model.addAttribute("sortingField", sortingField);
		model.addAttribute("sortingDirection", sortingDirection);
		model.addAttribute("reverseSortDirection", sortingDirection.equals("asc") ? "desc" : "asc");
		model.addAttribute("listEmployees", listEmployees);

		return "index";
	}
}
