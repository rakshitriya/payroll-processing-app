package org.dataorb.service;

import java.io.IOException;
import java.time.Month;
import java.util.List;
import java.util.Map;

import org.dataorb.entities.Employee;
import org.dataorb.exceptions.employee.EventProcessingException;
import org.dataorb.exceptions.employee.InvalidRecordFormatException;

public interface IEmployeeService {
    public void processEmployeeRecord(List<String> records) throws IOException, InvalidRecordFormatException, EventProcessingException;
    public int getTotalNumberOfEmployees();
    public Map<Month, List<Employee>> getMonthlyJoinersReport()  throws EventProcessingException;
    public Map<Month, List<Employee>> getMonthlyExitsReport()  throws EventProcessingException;
}
