package employeeManagement.employeemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import employeeManagement.employeemanagementsystem.entity.Employee;

public interface employeeRepository extends JpaRepository<Employee, Long> {

}
