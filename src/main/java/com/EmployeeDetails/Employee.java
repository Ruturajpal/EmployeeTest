package com.EmployeeDetails;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Employee ID is mandatory")
    private Long employeeId;

    @NotBlank(message = "First Name is mandatory")
    private String firstName;

    @NotBlank(message = "Last Name is mandatory")
    private String lastName;

    @Email(message = "Email is invalid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @ElementCollection
    @NotEmpty(message = "Phone number list cannot be empty")
    private List<String> phoneNumbers;

    @NotNull(message = "Date of Joining is mandatory")
    private LocalDate dateOfJoining;

    @NotNull(message = "Salary is mandatory")
    @Min(value = 0, message = "Salary must be non-negative")
    private Double salary;
}
