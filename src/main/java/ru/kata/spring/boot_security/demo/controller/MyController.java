package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Employee;
import ru.kata.spring.boot_security.demo.service.EmployeeService;

import java.security.Principal;
import java.util.List;

@Controller
public class MyController {

    private final EmployeeService employeeService;

    public MyController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/")
    public String welcomePage() {
        return "redirect:/login";
    }

    @GetMapping(value = "/admin")
    public String showAllEmployees(Model model) {
        List<Employee> allEmployees = employeeService.getAllEmployees();
        model.addAttribute("allEmps", allEmployees);
        return "all-employees";
    }

    @GetMapping(value = "/employee")
    public String showAllEmployees(Model model, Principal principal) {
        Employee employee = employeeService.findByLogin(principal.getName());
        model.addAttribute("employee", employee);
        return "employee";
    }

    @GetMapping("/admin/addNewEmployee")
    public String addNewEmployee(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "employee-info";
    }

    @PostMapping(value = "/admin/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/admin";
    }

    @GetMapping(value = "/admin/updateInfo")
    public String updateEmployee(@RequestParam("empID") int id, Model model) {
        Employee employee = employeeService.getEmployee(id);
        model.addAttribute("employee", employee);
        return "employee-info";
    }

    @GetMapping("/admin/deleteEmployee")
    public String deleteEmployee(@RequestParam("empID") int id) {
        employeeService.deleteEmployee(id);
        return "redirect:/admin";
    }
}
