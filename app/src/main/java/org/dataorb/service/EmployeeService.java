package org.dataorb.service;


import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dataorb.entities.Employee;
import org.dataorb.entities.EmployeeEvent;
import org.dataorb.entities.EventType;
import org.dataorb.exceptions.employee.EmployeeNotFoundException;
import org.dataorb.exceptions.employee.EventProcessingException;
import org.dataorb.exceptions.employee.InvalidRecordFormatException;
import org.dataorb.repository.IEmployeeRepository;
import org.dataorb.repository.IEventRepository;

public class EmployeeService implements IEmployeeService {

    private IEmployeeRepository employeeRepository;
    private IEventRepository eventRepository;
    private DateTimeFormatter onboardDateFormat = DateTimeFormatter.ofPattern("d-M-yyyy");
    private DateTimeFormatter eventDateFormat = DateTimeFormatter.ofPattern("d-M-yyyy");

    public EmployeeService(IEmployeeRepository employeeRepository, IEventRepository eventRepository) {
        this.employeeRepository = employeeRepository;
        this.eventRepository = eventRepository;
    }

    @Override
    public void processEmployeeRecord(List<String> records) throws IOException, InvalidRecordFormatException, EventProcessingException {
        for (String record : records) {
            try {
                record = record.replace("“", "\"").replace("”", "\"").trim();
                String[] fields = record.split(",");

                boolean containsOnboard = false;
                for (String field : fields) {
                    if (field.trim().equalsIgnoreCase("ONBOARD")) {
                        containsOnboard = true;
                        break; 
                    }
                }
                // Validate record format
                if (containsOnboard && fields.length != 9) {
                    throw new InvalidRecordFormatException("Record with 'ONBOARD' must have exactly 9 fields: " + record);
                }

                String empId = fields[1].trim();
                EventType eventType;

                if (record.contains("ONBOARD")) {
                    Employee employee = new Employee(empId, fields[2].trim(), fields[3].trim(), fields[4].trim());
                    employeeRepository.save(employee);
                    LocalDate dateOfEvent = LocalDate.parse(fields[7].trim(), onboardDateFormat);
                    EmployeeEvent employeeEvent = new EmployeeEvent(employee, EventType.ONBOARD, fields[6].trim(), dateOfEvent, fields[8].trim());
                    eventRepository.save(employeeEvent);
                } else {
                    // Other events
                    eventType = EventType.valueOf(fields[2].trim().toUpperCase());
                    LocalDate dateOfEvent = LocalDate.parse(fields[4].trim(), eventDateFormat);
                    Employee employee = employeeRepository.findById(empId)
                        .orElseThrow(() -> new EmployeeNotFoundException(empId));
                    EmployeeEvent employeeEvent = new EmployeeEvent(employee, eventType, fields[3].trim(), dateOfEvent, fields[5].trim());
                    eventRepository.save(employeeEvent);
                }
            } catch (Exception e) {
                throw new EventProcessingException("Error processing employee record: " + record, e);
            }
        }
    }

    @Override
    public int getTotalNumberOfEmployees() {
        return employeeRepository.getAllEmployeeIds().size();
    }

    @Override
    public Map<Month, List<Employee>> getMonthlyJoinersReport() throws EventProcessingException {
        Map<Month, List<Employee>> joinersReport = new HashMap<>();
        try {
            for (String empId : employeeRepository.getAllEmployeeIds()) {
                List<EmployeeEvent> events = eventRepository.findEventsByEmployeeId(empId);
                for (EmployeeEvent event : events) {
                    if (event.getEventType() == EventType.ONBOARD) {
                        LocalDate onboardDate = LocalDate.parse(event.getValue().trim(), DateTimeFormatter.ofPattern("d-M-yyyy"));
                        Month month = onboardDate.getMonth();
                        joinersReport.computeIfAbsent(month, k -> new ArrayList<>()).add(event.getEmployee());
                    }
                }
            }
        } catch (Exception e) {
            throw new EventProcessingException("Error generating monthly joiners report", e);
        }
        return joinersReport;
    }

    @Override
    public Map<Month, List<Employee>> getMonthlyExitsReport() throws EventProcessingException {
        Map<Month, List<Employee>> exitsReport = new HashMap<>();
        try {
            for (String empId : employeeRepository.getAllEmployeeIds()) {
                List<EmployeeEvent> events = eventRepository.findEventsByEmployeeId(empId);
                for (EmployeeEvent event : events) {
                    if (event.getEventType() == EventType.EXIT) {
                        LocalDate exitDate = LocalDate.parse(event.getValue().trim(), DateTimeFormatter.ofPattern("d-M-yyyy"));
                        Month month = exitDate.getMonth();
                        exitsReport.computeIfAbsent(month, k -> new ArrayList<>()).add(event.getEmployee());
                    }
                }
            }
        } catch (Exception e) {
            throw new EventProcessingException("Error generating monthly exits report", e);
        }
        return exitsReport;
    }
}
