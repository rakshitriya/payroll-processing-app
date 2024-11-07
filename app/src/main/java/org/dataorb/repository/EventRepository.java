package org.dataorb.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.dataorb.entities.EmployeeEvent;

public class EventRepository implements IEventRepository{

    private Map<String, List<EmployeeEvent>> eventStorage = new HashMap<>();

    @Override
    public void save(EmployeeEvent employeeEvent) {
        String empId = employeeEvent.getEmployee().getEmpId();
        eventStorage.computeIfAbsent(empId, k -> new ArrayList<>()).add(employeeEvent);
    }
    
    @Override
    public Optional<EmployeeEvent> findById(String empId) {
        List<EmployeeEvent> events = eventStorage.get(empId);
        return Optional.ofNullable(events != null && !events.isEmpty() ? events.get(0) : null);
    }
    
    @Override
    public void deleteById(String empId) {
        eventStorage.remove(empId);
    }

    @Override
    public void update(EmployeeEvent event) {
        // TODO Auto-generated method stub
    }

    @Override
    public List<EmployeeEvent> findAll() {
        List<EmployeeEvent> allEvents = new ArrayList<>();
        for (List<EmployeeEvent> events : eventStorage.values()) {
            allEvents.addAll(events);
        }
        return allEvents;
    }

    @Override
    public boolean existsById(String empId) {
        return eventStorage.containsKey(empId);
    }

    @Override
    public List<EmployeeEvent> findEventsByEmployeeId(String empId) {
        return eventStorage.getOrDefault(empId, new ArrayList<>());
    }

    @Override
    public void deleteEventsByEmployeeId(String empId) {
        eventStorage.remove(empId);
    }

    @Override
    public void deleteEvent(EmployeeEvent event) {
        List<EmployeeEvent> events = eventStorage.get(event.getEmployee().getEmpId());
        if (events != null) {
            events.remove(event);
            if (events.isEmpty()) {
                eventStorage.remove(event.getEmployee().getEmpId());
            }
        }
    }
    
}

