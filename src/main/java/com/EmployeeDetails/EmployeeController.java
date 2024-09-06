package com.EmployeeDetails;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
@Validated
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // Endpoint to store employee details
    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody @Valid Employee employee) {
        Employee savedEmployee = employeeService.saveEmployee(employee);
        return ResponseEntity.ok(savedEmployee);
    }

    // Endpoint to return employee tax deduction details
    @GetMapping("/{id}/tax")
    public ResponseEntity<Map<String, Object>> getEmployeeTax(@PathVariable Long id) {
        Employee employee = employeeService.getEmployee(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        double yearlySalary = employee.getSalary() * 12; // Full year salary
        double tax = employeeService.calculateTax(employee);
        double cess = employeeService.calculateCess(yearlySalary);

        Map<String, Object> response = new HashMap<>();
        response.put("employeeId", employee.getEmployeeId());
        response.put("firstName", employee.getFirstName());
        response.put("lastName", employee.getLastName());
        response.put("yearlySalary", yearlySalary);
        response.put("taxAmount", tax);
        response.put("cessAmount", cess);

        return ResponseEntity.ok(response);
    }
}
