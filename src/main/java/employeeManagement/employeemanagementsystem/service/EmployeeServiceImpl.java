package employeeManagement.employeemanagementsystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import employeeManagement.employeemanagementsystem.entity.Employee;
import employeeManagement.employeemanagementsystem.repository.employeeRepository;
@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private employeeRepository empRepo;
	
	public Employee saveEmployee(Employee employee) {
        return empRepo.save(employee);
    }
	
	public Optional<Employee>  getEmployeeById(Long id){
		return empRepo.findById(id);
		
	}
	
	public double calculateTax(double yearlySalary) {
        if (yearlySalary <= 250000) {
            return 0;
        } else if (yearlySalary <= 500000) {
            return (yearlySalary - 250000) * 0.05;
        } else if (yearlySalary <= 1000000) {
            return 12500 + (yearlySalary - 500000) * 0.10;
        } else {
            return 62500 + (yearlySalary - 1000000) * 0.20;
        }
    }
	
	public double calculateCess(double yearlySalary) {
        if (yearlySalary > 2500000) {
            return (yearlySalary - 2500000) * 0.02;
        }
        return 0;
	}

}
