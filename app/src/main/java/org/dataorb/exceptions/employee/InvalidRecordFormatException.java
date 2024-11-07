package org.dataorb.exceptions.employee;

public class InvalidRecordFormatException extends Exception{
    public InvalidRecordFormatException(String message) {
        super(message);
    }
    
    public InvalidRecordFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
