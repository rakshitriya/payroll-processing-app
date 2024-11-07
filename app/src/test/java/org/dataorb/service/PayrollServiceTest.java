package org.dataorb.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.dataorb.exceptions.employee.EventProcessingException;
import org.dataorb.exceptions.employee.InvalidRecordFormatException;
import org.dataorb.exceptions.payroll.EmployeeEventProcessingException;
import org.dataorb.repository.EmployeeRepository;
import org.dataorb.repository.EventRepository;
import org.dataorb.repository.IEmployeeRepository;
import org.dataorb.repository.IEventRepository;
import org.dataorb.response.FinancialReport;
import org.dataorb.response.SalaryReport;
import org.dataorb.response.YearlyEventReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PayrollServiceTest {
    private IEmployeeRepository employeeRepository;
    
    private IEventRepository eventRepository;

    @InjectMocks
    private EmployeeService employeeService;

    @InjectMocks
    private PayrollService payrollService;

    @BeforeEach
    public void setUp() throws IOException, InvalidRecordFormatException, EventProcessingException{
        employeeRepository = new EmployeeRepository();
        eventRepository = new EventRepository();
        employeeService = new EmployeeService(employeeRepository, eventRepository);
        payrollService = new PayrollService(employeeRepository, eventRepository);
        List<String> records = Arrays.asList(
            "1, emp101, Bill, Gates, Software Engineer, ONBOARD, 1-11-2022, 10-10-2022, \"Bill Gates is going to join DataOrb on 1st November as a SE.\"",
            "2, emp102, Steve, Jobs, Architect, ONBOARD, 1-10-2022, 10-10-2022, \"Steve Jobs joined DataOrb on 1st October as an Architect.\"",
            "3, emp103, Martin, Fowler, Software Engineer, ONBOARD, 15-10-2022, 10-10-2022, \"Martin has joined DataOrb as a SE.\"",
            "4, emp102,  EXIT, 1-12-2022, 10-10-2022, \"Steve is leaving on 1st December.\"",
            "5, emp103,  SALARY, 5000, 10-10-2022, \"Oct salary for Martin.\"",
            "6, emp101,  SALARY, 3000, 10-9-2022, \"Sept salary for Bill.\"",
            "7, emp101,  SALARY, 3000, 10-10-2022, \"Oct salary for Bill.\""
            
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
    public void testGetMonthlySalaryReport(){
        Map<Month, SalaryReport> salaryReport = payrollService.getMonthlySalaryReport();
        assertNotNull(salaryReport);
        assertEquals(2, salaryReport.size()); // Sept and Oct
        SalaryReport octoberReport = salaryReport.get(Month.OCTOBER);
        assertEquals(8000, octoberReport.getTotalSalary()); // octobe salary 5k+3k
        assertEquals(2, octoberReport.getTotalEmployees());
    }

    @Test
    public void testGetEmployeeWiseFinancialReport() throws EmployeeEventProcessingException{
        Map<String, FinancialReport> financialReportMap = payrollService.getEmployeeWiseFinancialReport();
        assertEquals(3, financialReportMap.size());
    }

    @Test
    public void testGetMonthlyAmountReleased(){
        Map<Month, SalaryReport> amountReport = payrollService.getMonthlyAmountReleasedReport();
        assertTrue(amountReport.containsKey(Month.SEPTEMBER)); 
        assertTrue(amountReport.containsKey(Month.OCTOBER));
    }

    @Test
    public void testYearlyFinancialReport(){
        List<YearlyEventReport> yearlyReport = payrollService.getYearlyFinancialReport();
        assertEquals(7, yearlyReport.size());
    }
}
