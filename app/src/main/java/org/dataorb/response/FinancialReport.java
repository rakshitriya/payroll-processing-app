package org.dataorb.response;

public class FinancialReport {
    private String firstName;
    private String lastName;
    private double totalAmountPaid;

    public FinancialReport(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.totalAmountPaid = 0.0;
    }

    public void addAmountPaid(double amount) {
        this.totalAmountPaid += amount;
    }

    public double getTotalAmountPaid() {
        return totalAmountPaid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}

