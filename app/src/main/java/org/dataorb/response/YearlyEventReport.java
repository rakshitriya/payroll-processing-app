package org.dataorb.response;

import java.time.LocalDate;

import org.dataorb.entities.EventType;

public class YearlyEventReport {
    private EventType eventType;
    private String empId;
    private LocalDate eventDate;
    private double eventValue;

    public YearlyEventReport(EventType eventType, String empId, LocalDate eventDate, double eventValue) {
        this.eventType = eventType;
        this.empId = empId;
        this.eventDate = eventDate;
        this.eventValue = eventValue;
    }

    public EventType getEventType() {
        return eventType;
    }

    public String getEmpId() {
        return empId;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public double getEventValue() {
        return eventValue;
    }
}

