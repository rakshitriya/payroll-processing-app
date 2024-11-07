package org.dataorb.exceptions.payroll;

public class EmployeeEventProcessingException extends Exception {
    public EmployeeEventProcessingException(String message) {
        super(message);
    }

    public EmployeeEventProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}