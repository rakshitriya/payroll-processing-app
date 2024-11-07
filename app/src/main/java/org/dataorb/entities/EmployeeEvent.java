package org.dataorb.entities;

import java.time.LocalDate;

public class EmployeeEvent {
    private Employee employee;
    private EventType eventType;  
    private String value;
    private LocalDate eventDate;
    private String notes;

    public EmployeeEvent(Employee employee, EventType eventType, String value, LocalDate eventDate, String notes){
        this.employee = employee;
        this.eventType = eventType;
        this.value = value;
        this.eventDate = eventDate;
        this.notes=notes;
    }
    // Getters and Setters

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "EmployeeEvent{" +
                ", empId='" + employee.getEmpId() + '\'' +
                ", eventType='" + eventType + '\'' +
                ", value=" + value +
                ", eventDate=" + eventDate +
                ", notes='" + notes + '\'' +
                '}';
    }
}
