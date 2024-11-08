package org.dataorb.service;

import java.time.Month;
import java.util.List;
import java.util.Map;

import org.dataorb.entities.Employee;
import org.dataorb.entities.EmployeeEvent;
import org.dataorb.exceptions.employee.EmployeeNotFoundException;
import org.dataorb.repository.IEmployeeRepository;
import org.dataorb.repository.IEventRepository;
import org.dataorb.response.FinancialReport;
import org.dataorb.response.SalaryReport;
import org.dataorb.response.YearlyEventReport;

public class DisplayService {
    private IEmployeeRepository employeeRepository;
    private IEventRepository eventRepository;
    private IEmployeeService employeeService;
    private IPayrollService payrollService;

    public DisplayService(IEmployeeRepository employeeRepository, IEventRepository eventRepository, 
                          IEmployeeService employeeService, IPayrollService payrollService) {
        this.employeeRepository = employeeRepository;
        this.eventRepository = eventRepository;
        this.employeeService = employeeService;
        this.payrollService = payrollService;
    }

    public void displayProcessedEmployees() {
        System.out.println("Employees processed: ");
        for (String empId : employeeRepository.getAllEmployeeIds()) {
            Employee emp = employeeRepository.findById(empId)
                            .orElseThrow(() -> new EmployeeNotFoundException(empId)); // Using Optional to handle EmployeeNotFoundException
            System.out.println(emp.getFirstName() + " " + emp.getLastName() + " - " + emp.getDesignation());
            List<EmployeeEvent> events = eventRepository.findEventsByEmployeeId(empId);
            if (events == null || events.isEmpty()) {
                System.out.println("    No events found for this employee.");
            } else {
                for (EmployeeEvent event : events) {
                    System.out.println("    Event: " + event.getEventType() + " on " + event.getValue() + " - " + event.getNotes());
                }
            }
        }
    }

    public void displayTotalEmployees(){
        System.out.println("\nTotal Employees are: "+employeeService.getTotalNumberOfEmployees());
    }

    public void displayMonthlyJoinersReport() {
        try {
            Map<Month, List<Employee>> joinersReport = employeeService.getMonthlyJoinersReport();
            System.out.println("\nMonthly Joiners Report:");
            for (Map.Entry<Month, List<Employee>> entry : joinersReport.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue().size() + " joiners");
            }
        } catch (Exception e) {
            System.out.println("Error generating Monthly Joiners Report: " + e.getMessage());
        }
    }

    public void displayMonthlyExitsReport() {
        try {
            Map<Month, List<Employee>> exitsReport = employeeService.getMonthlyExitsReport();
            System.out.println("\nMonthly Exits Report:");
            for (Map.Entry<Month, List<Employee>> entry : exitsReport.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue().size() + " exits");
            }
        } catch (Exception e) {
            System.out.println("Error generating Monthly Exits Report: " + e.getMessage());
        }
    }

    public void displayMonthlySalaryReport() {
        try {
            Map<Month, SalaryReport> salaryReport = payrollService.getMonthlySalaryReport();
            System.out.println("\nMonthly Salary Report:");
            for (Map.Entry<Month, SalaryReport> entry : salaryReport.entrySet()) {
                System.out.println(entry.getKey() + ": Total Salary = " + entry.getValue().getTotalSalary() +
                                   ", Total Employees = " + entry.getValue().getTotalEmployees());
            }
        } catch (Exception e) {
            System.out.println("Error generating Monthly Salary Report: " + e.getMessage());
        }
    }

    public void displayEmployeeWiseFinancialReport() {
        try {
            System.out.println("\nEmployee-Wise Financial Report:");
            Map<String, FinancialReport> financialReportMap = payrollService.getEmployeeWiseFinancialReport();
            for (String empId : financialReportMap.keySet()) {
                FinancialReport report = financialReportMap.get(empId);
                System.out.println("Emp ID: " + empId + ", Name: " + report.getFirstName() + " " + report.getLastName() + ", Total Amount Paid: " + report.getTotalAmountPaid());
            }
        } catch (Exception e) {
            System.out.println("Error generating Employee-Wise Financial Report: " + e.getMessage());
        }
    }

    public void displayMonthlyAmountReleasedReport() {
        try {
            System.out.println("\nMonthly Amount Released Report:");
            Map<Month, SalaryReport> amountReport = payrollService.getMonthlyAmountReleasedReport();
            for (Month month : amountReport.keySet()) {
                SalaryReport report = amountReport.get(month);
                System.out.println("Month: " + month + ", Total Amount Released: " + report.getTotalSalary() + ", Total Employees: " + report.getTotalEmployees());
            }
        } catch (Exception e) {
            System.out.println("Error generating Monthly Amount Released Report: " + e.getMessage());
        }
    }

    public void displayYearlyFinancialReport() {
        try {
            System.out.println("\nYearly Financial Report:");
            List<YearlyEventReport> yearlyReport = payrollService.getYearlyFinancialReport();
            for (YearlyEventReport report : yearlyReport) {
                System.out.println("Event: " + report.getEventType()
                               + ", Emp ID: " + report.getEmpId()
                               + ", Event Date: " + report.getEventDate()
                               + ", Event Value: " + report.getEventValue());
            }
        } catch (Exception e) {
            System.out.println("Error generating Yearly Financial Report: " + e.getMessage());
        }
    }
}
