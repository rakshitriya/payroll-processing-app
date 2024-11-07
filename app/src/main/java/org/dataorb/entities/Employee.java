package org.dataorb.entities;

public class Employee {
    private String empId;      // Unique ID of the employee
    private String firstName;  // First Name of the employee
    private String lastName;   // Last Name of the employee
    private String designation; // Designation or job title of the employee

    // Constructor
    public Employee(String empId, String firstName, String lastName, String designation) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.designation = designation;
    }

    public Employee() {
        //TODO Auto-generated constructor stub
    }

    // Getters and setters
    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId='" + empId + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }
}
