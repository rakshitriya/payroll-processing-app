package org.dataorb.exceptions.payroll;

public class MissingSalaryEventException extends RuntimeException {
    public MissingSalaryEventException(String empId) {
        super("Salary event missing for employee with ID " + empId);
    }
}