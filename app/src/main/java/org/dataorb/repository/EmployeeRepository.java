package org.dataorb.repository;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.dataorb.entities.Employee;

public class EmployeeRepository implements IEmployeeRepository {

    private Map<String, Employee> employeeStorage = new HashMap<>();

    @Override
    public void save(Employee employee) {
        employeeStorage.put(employee.getEmpId(), employee);
    }

    
    @Override
    public boolean existsById(String empId) {
        return employeeStorage.containsKey(empId);
    }

    @Override
    public void deleteById(String empId) {
        employeeStorage.remove(empId);
    }

    @Override
    public void update(Employee employee) {
        employeeStorage.put(employee.getEmpId(), employee);
    }

    @Override
    public List<Employee> findAll(){
        return new ArrayList<>(employeeStorage.values());
    }
    @Override
    public List<String> getAllEmployeeIds() {
    return new ArrayList<>(employeeStorage.keySet());
    }
    @Override
    public Optional<Employee>  findById(String empId) {
        return Optional.ofNullable(employeeStorage.get(empId));
    }

}
