package org.dataorb.service;

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
import org.dataorb.exceptions.payroll.EmployeeEventProcessingException;
import org.dataorb.repository.IEmployeeRepository;
import org.dataorb.repository.IEventRepository;
import org.dataorb.response.FinancialReport;
import org.dataorb.response.SalaryReport;
import org.dataorb.response.YearlyEventReport;

public class PayrollService implements IPayrollService {

    private IEmployeeRepository employeeRepository;
    private IEventRepository eventRepository;
    public PayrollService(IEmployeeRepository employeeRepository, IEventRepository eventRepository) {
        this.employeeRepository = employeeRepository;
        this.eventRepository = eventRepository;
    }
    // @Override
    // public Map<Month, SalaryReport> getMonthlySalaryReport() throws InvalidSalaryAmountException, MissingSalaryEventException, EmployeeEventProcessingException {
    //     Map<Month, SalaryReport> salaryReport = new HashMap<>();
    //     try {
    //         for (String empId : employeeRepository.getAllEmployeeIds()) {
    //             List<EmployeeEvent> events = eventRepository.findEventsByEmployeeId(empId);
    //             boolean salaryEventFound = false;

    //             for (EmployeeEvent event : events) {
    //                 if (event.getEventType() == EventType.SALARY) {
    //                     salaryEventFound = true;
    //                     try {
    //                         System.out.println(event.getEventType());
    //                         double salary = Double.parseDouble(event.getValue());
    //                         Month month = event.getEventDate().getMonth();

    //                         SalaryReport report = salaryReport.computeIfAbsent(month, m -> new SalaryReport());
    //                         report.addSalary(salary);
    //                         report.incrementEmployeeCount();
    //                     } catch (NumberFormatException e) {
    //                         throw new InvalidSalaryAmountException("Invalid salary amount for employee " + empId);
    //                     }
    //                 }
    //             }

    //             if (!salaryEventFound) {
    //                 throw new MissingSalaryEventException(empId);
    //             }
    //         }
    //     } catch (Exception e) {
    //         throw new EmployeeEventProcessingException("Error generating monthly salary report", e);
    //     }
    //     return salaryReport;
    // }

    @Override
    public Map<Month, SalaryReport> getMonthlySalaryReport(){
        Map<Month, SalaryReport> salaryReport = new HashMap<>();
        for (String empId : employeeRepository.getAllEmployeeIds()) {
            List<EmployeeEvent> events = eventRepository.findEventsByEmployeeId(empId);

                for (EmployeeEvent event : events) {
                    if (event.getEventType() == EventType.SALARY) {
                            System.out.println(event.getEventType());
                            double salary = Double.parseDouble(event.getValue());
                            Month month = event.getEventDate().getMonth();
                            SalaryReport report = salaryReport.computeIfAbsent(month, m -> new SalaryReport());
                            report.addSalary(salary);
                            report.incrementEmployeeCount();
                    }
                }
        }
        return salaryReport;
    }

    @Override
    public Map<String, FinancialReport> getEmployeeWiseFinancialReport() throws EmployeeEventProcessingException {
        Map<String, FinancialReport> financialReportMap = new HashMap<>();
        try {
            for (String empId : employeeRepository.getAllEmployeeIds()) {
                Employee employee = employeeRepository.findById(empId)
                        .orElseThrow(() -> new EmployeeNotFoundException(empId));
                List<EmployeeEvent> events = eventRepository.findEventsByEmployeeId(empId);
                FinancialReport report = financialReportMap.computeIfAbsent(empId, id -> new FinancialReport(employee.getFirstName(), employee.getLastName()));
                for (EmployeeEvent event : events) {
                    if (event.getEventType() == EventType.SALARY || event.getEventType() == EventType.BONUS || event.getEventType() == EventType.REIMBURSEMENT) {
                        double amount = Double.parseDouble(event.getValue());
                        report.addAmountPaid(amount);
                    }
                }
            }
        } catch (Exception e) {
            throw new EmployeeEventProcessingException("Error generating employee wise financial report", e);
        }
        return financialReportMap;
    }
    @Override
    public Map<Month, SalaryReport> getMonthlyAmountReleasedReport() {
        Map<Month, SalaryReport> amountReport = new HashMap<>();
        for (String empId : employeeRepository.getAllEmployeeIds()) {
            List<EmployeeEvent> events = eventRepository.findEventsByEmployeeId(empId);
            for (EmployeeEvent event : events) {
                if (event.getEventType() == EventType.SALARY || event.getEventType() == EventType.BONUS || event.getEventType() == EventType.REIMBURSEMENT) {
                    Month month = event.getEventDate().getMonth();
                    double amount = Double.parseDouble(event.getValue());
                    SalaryReport report = amountReport.computeIfAbsent(month, m -> new SalaryReport());
                    report.addSalary(amount);
                    report.incrementEmployeeCount();
                }
            }
        }
        return amountReport;
    }
    @Override
    public List<YearlyEventReport> getYearlyFinancialReport() {
        List<YearlyEventReport> yearlyReport = new ArrayList<>();
        for (String empId : employeeRepository.getAllEmployeeIds()) {
            List<EmployeeEvent> events = eventRepository.findEventsByEmployeeId(empId);
            for (EmployeeEvent event : events) {
                if(event.getEventType()==EventType.ONBOARD){
                    LocalDate onboardEventDate = LocalDate.parse(event.getValue().trim(), DateTimeFormatter.ofPattern("d-M-yyyy"));
                    yearlyReport.add(new YearlyEventReport(event.getEventType(), empId, onboardEventDate, 0));
                }else if(event.getEventType()==EventType.EXIT){
                    LocalDate exitEventDate = LocalDate.parse(event.getValue().trim(), DateTimeFormatter.ofPattern("d-M-yyyy"));
                    yearlyReport.add(new YearlyEventReport(event.getEventType(), empId, exitEventDate, 0));
                }else{
                    yearlyReport.add(new YearlyEventReport(event.getEventType(), empId, event.getEventDate(), Double.parseDouble(event.getValue())));
                }
            }
        }
        return yearlyReport;
    }
}

