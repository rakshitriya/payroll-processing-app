package org.dataorb.service;

import java.time.Month;
import java.util.List;
import java.util.Map;

import org.dataorb.exceptions.payroll.EmployeeEventProcessingException;
import org.dataorb.exceptions.payroll.InvalidSalaryAmountException;
import org.dataorb.exceptions.payroll.MissingSalaryEventException;
import org.dataorb.response.FinancialReport;
import org.dataorb.response.SalaryReport;
import org.dataorb.response.YearlyEventReport;

public interface IPayrollService {
    public Map<Month, SalaryReport> getMonthlySalaryReport()throws InvalidSalaryAmountException, MissingSalaryEventException, EmployeeEventProcessingException;
    public Map<String, FinancialReport> getEmployeeWiseFinancialReport() throws EmployeeEventProcessingException;
    public Map<Month, SalaryReport> getMonthlyAmountReleasedReport();
    public List<YearlyEventReport> getYearlyFinancialReport();
}
