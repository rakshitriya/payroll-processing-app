package org.dataorb.exceptions.payroll;

public class InvalidSalaryAmountException extends RuntimeException {
    public InvalidSalaryAmountException(String message) {
        super(message);
    }
}