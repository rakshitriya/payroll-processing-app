package org.dataorb.repository;

import java.util.List;
import java.util.Optional;

import org.dataorb.entities.Employee;

public interface IEmployeeRepository extends ICrudRepository<Employee, String> {
    public List<String> getAllEmployeeIds();
    public Optional<Employee>  findById(String empId);
}
