package employeeManagement.employeemanagementsystem.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import employeeManagement.employeemanagementsystem.service.EmployeeServiceImpl;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class employeeController {
    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @PostMapping
    public ResponseEntity<employeeManagement.employeemanagementsystem.entity.Employee> createEmployee(@Validated @RequestBody employeeManagement.employeemanagementsystem.entity.Employee employee) {
        employeeManagement.employeemanagementsystem.entity.Employee savedEmployee = employeeServiceImpl.saveEmployee(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    @GetMapping("/{id}/tax")
    public ResponseEntity<?> getEmployeeTax(@PathVariable Long id) {
        return employeeServiceImpl.getEmployeeById(id).map(employee -> {
            LocalDate now = LocalDate.now();
            LocalDate startOfFinancialYear = LocalDate.of(now.getYear(), 4, 1);
            if (now.isBefore(startOfFinancialYear)) {
                startOfFinancialYear = startOfFinancialYear.minusYears(1);
            }

            LocalDate doj = employee.getDoj().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (doj.isAfter(startOfFinancialYear)) {
                startOfFinancialYear = doj.withDayOfMonth(1).plusMonths(1);
            }

            long monthsWorked = ChronoUnit.MONTHS.between(startOfFinancialYear, now);
            double yearlySalary = employee.getSalary() * monthsWorked;

            double tax = employeeServiceImpl.calculateTax(yearlySalary);
            double cess = employeeServiceImpl.calculateCess(yearlySalary);

            Map<String, Object> response = new HashMap<>();
            response.put("employeeId", employee.getId());
            response.put("firstName", employee.getFirstName());
            response.put("lastName", employee.getLastName());
            response.put("yearlySalary", yearlySalary);
            response.put("taxAmount", tax);
            response.put("cessAmount", cess);

            return ResponseEntity.ok(response);
        }).orElse(ResponseEntity.notFound().build());
    }
}
