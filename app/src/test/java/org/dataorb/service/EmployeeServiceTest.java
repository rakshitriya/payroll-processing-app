package org.dataorb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import java.io.IOException;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.dataorb.entities.Employee;
import org.dataorb.exceptions.employee.EventProcessingException;
import org.dataorb.exceptions.employee.InvalidRecordFormatException;
import org.dataorb.repository.EmployeeRepository;
import org.dataorb.repository.EventRepository;
import org.dataorb.repository.IEmployeeRepository;
import org.dataorb.repository.IEventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest{
    
    
    private IEmployeeRepository employeeRepository;
    
    private IEventRepository eventRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @BeforeEach
    public void setUp() throws IOException, InvalidRecordFormatException, EventProcessingException{
        employeeRepository = new EmployeeRepository();
        eventRepository = new EventRepository();
        employeeService = new EmployeeService(employeeRepository, eventRepository);
        List<String> records = Arrays.asList(
            "1, emp101, Bill, Gates, Software Engineer, ONBOARD, 1-11-2022, 10-10-2022, \"Bill Gates is going to join DataOrb on 1st November as a SE.\"",
            "2, emp102, Steve, Jobs, Architect, ONBOARD, 1-10-2022, 10-10-2022, \"Steve Jobs joined DataOrb on 1st October as an Architect.\"",
            "3, emp103, Martin, Fowler, Software Engineer, ONBOARD, 15-10-2022, 10-10-2022, \"Martin has joined DataOrb as a SE.\"",
            "4, emp102,  EXIT, 1-12-2022, 10-10-2022, \"Steve is leaving on 1st December.\""
            
        );

        // Test the process
        //verify(employeeRepository, times(3)).save(any(Employee.class));
        employeeService.processEmployeeRecord(records);
    }
    @Test
    public void checkIfRepoIsEmpty(){
        assertNotNull(employeeRepository); 
        assertNotNull(eventRepository);
    }
    @Test
    public void testProcessEmployeeRecord() throws IOException, InvalidRecordFormatException, EventProcessingException{
        assertTrue(employeeRepository.findAll().size() == 3);
    }
    @Test
    public void testGetTotalNumberOfEmployees() {
        // Test if the total number of employees is correct
        int totalEmployees = employeeService.getTotalNumberOfEmployees();
        assertEquals(3, totalEmployees);  // We added 3 employees in setUp()
    }
    
    @Test
    public void testGetMonthlyJoinersReport() throws EventProcessingException {
        // Call the method under test
        Map<Month, List<Employee>> joinersReport = employeeService.getMonthlyJoinersReport();

        // Verify that the contains the correct number of months
        assertNotNull(joinersReport);
        assertEquals(2, joinersReport.size());  // Should have 2 months (October and November)

        
        List<Employee> octoberJoiners = joinersReport.get(Month.OCTOBER);
        assertEquals(2, octoberJoiners.size());  // Steve Jobs and Martin Fowler joined in October
        assertTrue(octoberJoiners.contains(employeeRepository.findById("emp102").orElse(null)));
        assertTrue(octoberJoiners.contains(employeeRepository.findById("emp103").orElse(null)));

        List<Employee> novemberJoiners = joinersReport.get(Month.NOVEMBER);
        assertEquals(1, novemberJoiners.size());  // Bill Gates joined in November
        assertTrue(novemberJoiners.contains(employeeRepository.findById("emp101").orElse(null)));
    }
    @Test
    public void testGetMonthlyExitsReport() throws EventProcessingException {
        // Call the method under test
        Map<Month, List<Employee>> exitsReport = employeeService.getMonthlyExitsReport();

        // Verify that the report is not null and contains the correct number of months
        assertNotNull(exitsReport);
        assertEquals(1, exitsReport.size());  // Should have 1 month (December)

        // Verify employees are grouped by the correct month
        List<Employee> decemberExits = exitsReport.get(Month.DECEMBER);
        assertEquals(1, decemberExits.size());  // Anonymous Employee exited in December

        // Use Optional.get() or orElse(null) to extract the employee
        assertTrue(decemberExits.contains(employeeRepository.findById("emp102").orElse(null)));
    }
}