package org.dataorb.exceptions.employee;

public class EmployeeNotFoundException extends RuntimeException{
    public EmployeeNotFoundException(String empId) {
        super("Employee with ID " + empId + " not found.");
    }
}
