package ru.kata.spring.boot_security.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kata.spring.boot_security.demo.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    public Employee findByLogin(String login);

    public  Employee getEmployeeById(int id);
}
