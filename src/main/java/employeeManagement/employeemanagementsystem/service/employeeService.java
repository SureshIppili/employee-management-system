package employeeManagement.employeemanagementsystem.service;

import java.util.Optional;

import employeeManagement.employeemanagementsystem.entity.Employee;

public interface EmployeeService {
	
	public Employee saveEmployee(Employee employee);
	
	public Optional<Employee>  getEmployeeById(Long id);
	
	public double calculateTax(double yearlySalary);
	
	public double calculateCess(double yearlySalary);

}
