/*
 * This source file was generated by the Gradle 'init' task
 */
package org.dataorb;

import java.io.IOException;
import java.util.List;
import org.dataorb.exceptions.employee.EventProcessingException;
import org.dataorb.exceptions.employee.InvalidRecordFormatException;
import org.dataorb.parser.IParser;
import org.dataorb.parser.TxtParser;
import org.dataorb.repository.IEmployeeRepository;
import org.dataorb.repository.EmployeeRepository;
import org.dataorb.repository.EventRepository;
import org.dataorb.repository.IEventRepository;
import org.dataorb.service.DisplayService;
import org.dataorb.service.EmployeeService;
import org.dataorb.service.IEmployeeService;
import org.dataorb.service.IPayrollService;
import org.dataorb.service.PayrollService;

public class PayrollProcessor {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InvalidRecordFormatException, EventProcessingException {
        if (args.length < 1) {
            System.out.println("Usage: java PayrollProcessor <file-path>");
            System.exit(1);
        }

        String filePath = args[0];

        IEmployeeRepository employeeRepository = new EmployeeRepository();
        IEventRepository eventRepository = new EventRepository();

        IParser parser = new TxtParser();
        List<String> records = parser.parse(filePath);

        IPayrollService payrollService = new PayrollService(employeeRepository, eventRepository);
        IEmployeeService employeeService = new EmployeeService(employeeRepository, eventRepository);

        DisplayService displayService = new DisplayService(employeeRepository, eventRepository, employeeService, payrollService);

        employeeService.processEmployeeRecord(records);

        // Display Reports
        displayService.displayProcessedEmployees();
        displayService.displayTotalEmployees();
        displayService.displayMonthlyJoinersReport();
        displayService.displayMonthlyExitsReport();
        displayService.displayMonthlySalaryReport();
        displayService.displayEmployeeWiseFinancialReport();
        displayService.displayMonthlyAmountReleasedReport();
        displayService.displayYearlyFinancialReport();
    }
}
