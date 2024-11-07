package org.dataorb.repository;

import java.util.List;

import org.dataorb.entities.EmployeeEvent;

public interface IEventRepository extends ICrudRepository<EmployeeEvent, String>{
    public boolean existsById(String empId);
    public List<EmployeeEvent> findEventsByEmployeeId(String empId);
    public void deleteEventsByEmployeeId(String empId);
    public void deleteEvent(EmployeeEvent event);
}
