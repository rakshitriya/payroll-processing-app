package org.dataorb.repository;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.dataorb.entities.Employee;

import java.util.List;
import java.util.Optional;

public class EmployeeRepositorytest {

    private IEmployeeRepository employeeRepository;

    @BeforeEach
    public void setUp() {
        employeeRepository = new EmployeeRepository();
    }

    @Test
    public void testSaveEmployee() {
        Employee employee = new Employee("emp101", "Bill", "Gates", "Software Engineer");
        employeeRepository.save(employee);
        Optional<Employee> savedEmployee = employeeRepository.findById("emp101");
        assertTrue(savedEmployee.isPresent(), "Employee should be present in the repository.");
        assertEquals("emp101", savedEmployee.get().getEmpId(), "Employee ID should match.");
        assertEquals("Bill", savedEmployee.get().getFirstName(), "First name should match.");
        assertEquals("Gates", savedEmployee.get().getLastName(), "Last name should match.");
    }

    @Test
    public void testExistsByIdTrue() {
        Employee employee = new Employee("emp102", "Steve", "Jobs", "Architect");
        employeeRepository.save(employee);
        assertTrue(employeeRepository.existsById("emp102"), "Employee should exist.");
    }

    @Test
    public void testExistsByIdFalse() {
        assertFalse(employeeRepository.existsById("emp999"), "Employee should not exist.");
    }

    @Test
    public void testFindById() {
        Employee employee = new Employee("emp103", "Martin", "Fowler", "Software Engineer");
        employeeRepository.save(employee);
        Optional<Employee> foundEmployee = employeeRepository.findById("emp103");
        assertTrue(foundEmployee.isPresent(), "Employee should be found.");
        assertEquals("emp103", foundEmployee.get().getEmpId(), "Employee ID should match.");
    }

    @Test
    public void testFindByIdNotFound() {
        Optional<Employee> foundEmployee = employeeRepository.findById("emp999");
        assertFalse(foundEmployee.isPresent(), "Employee should not be found.");
    }

    @Test
    public void testUpdateEmployee() {
        Employee employee = new Employee("emp104", "John", "Doe", "Manager");
        employeeRepository.save(employee);
        employee.setFirstName("Jonathan");
        employee.setLastName("Doe-Smith");
        employeeRepository.update(employee);
        Optional<Employee> updatedEmployee = employeeRepository.findById("emp104");
        assertTrue(updatedEmployee.isPresent(), "Employee should be found.");
        assertEquals("Jonathan", updatedEmployee.get().getFirstName(), "First name should be updated.");
        assertEquals("Doe-Smith", updatedEmployee.get().getLastName(), "Last name should be updated.");
    }

    @Test
    public void testDeleteById() {
        Employee employee = new Employee("emp105", "Alice", "Wonderland", "Developer");
        employeeRepository.save(employee);
        assertTrue(employeeRepository.existsById("emp105"), "Employee should exist before deletion.");
        employeeRepository.deleteById("emp105");
        assertFalse(employeeRepository.existsById("emp105"), "Employee should not exist after deletion.");
    }

    @Test
    public void testFindAllEmployees() {
        Employee employee1 = new Employee("emp106", "Charlie", "Brown", "Designer");
        Employee employee2 = new Employee("emp107", "Lucy", "Van Pelt", "Psychologist");
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        List<Employee> allEmployees = employeeRepository.findAll();
        assertEquals(2, allEmployees.size(), "There should be 2 employees in the repository.");
        assertTrue(allEmployees.contains(employee1), "Employee 1 should be in the list.");
        assertTrue(allEmployees.contains(employee2), "Employee 2 should be in the list.");
    }

    @Test
    public void testGetAllEmployeeIds() {
        Employee employee1 = new Employee("emp108", "Paul", "Atreides", "Duke");
        Employee employee2 = new Employee("emp109", "Chani", "Fremen", "Mentat");
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        List<String> employeeIds = employeeRepository.getAllEmployeeIds();
        assertTrue(employeeIds.contains("emp108"), "Employee ID emp108 should be in the list.");
        assertTrue(employeeIds.contains("emp109"), "Employee ID emp109 should be in the list.");
    }
}
