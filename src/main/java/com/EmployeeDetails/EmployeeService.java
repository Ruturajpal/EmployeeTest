package com.EmployeeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Optional<Employee> getEmployee(Long id) {
        return employeeRepository.findById(id);
    }

    public double calculateTax(Employee employee) {
        LocalDate currentDate = LocalDate.now();
        LocalDate doj = employee.getDateOfJoining();

        // Check if date of joining is after the current date (invalid case)
        if (doj.isAfter(currentDate)) {
            throw new RuntimeException("Date of Joining cannot be in the future.");
        }

        // Get the number of months worked, considering only full months.
        long fullMonthsWorked = ChronoUnit.MONTHS.between(doj, currentDate);

        // If the employee joined in the middle of the month, calculate the days worked in the joining month
        int daysWorkedInFirstMonth = 0;
        if (doj.getDayOfMonth() > 1) {
            daysWorkedInFirstMonth = currentDate.getDayOfMonth(); // Days worked in the current month
            fullMonthsWorked -= 1; // Exclude the partial month from full months worked
        }

        // Calculate salary based on full months worked + partial days in the first month
        double totalSalary = (employee.getSalary() * fullMonthsWorked) + (employee.getSalary() / 30 * daysWorkedInFirstMonth);

        double tax = 0;

        // Calculate tax based on slabs
        if (totalSalary > 250000) {
            if (totalSalary > 1000000) {
                tax += (totalSalary - 1000000) * 0.20;
                totalSalary = 1000000;
            }
            if (totalSalary > 500000) {
                tax += (totalSalary - 500000) * 0.10;
                totalSalary = 500000;
            }
            if (totalSalary > 250000) {
                tax += (totalSalary - 250000) * 0.05;
            }
        }

        return tax;
    }

    public double calculateCess(double yearlySalary) {
        if (yearlySalary > 2500000) {
            return (yearlySalary - 2500000) * 0.02;
        }
        return 0;
    }
}
