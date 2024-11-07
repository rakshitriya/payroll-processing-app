package org.dataorb.response;

public class SalaryReport {
    private double totalSalary;
    private int totalEmployees;

    public SalaryReport() {
        this.totalSalary = 0.0;
        this.totalEmployees = 0;
    }

    public void addSalary(double salary) {
        this.totalSalary += salary;
    }

    public void incrementEmployeeCount() {
        this.totalEmployees++;
    }

    public double getTotalSalary() {
        return totalSalary;
    }

    public int getTotalEmployees() {
        return totalEmployees;
    }
}
