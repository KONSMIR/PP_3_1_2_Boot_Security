package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Employee;
import ru.kata.spring.boot_security.demo.service.EmployeeService;
import ru.kata.spring.boot_security.demo.service.RoleService;

@Controller
@PreAuthorize("ADMIN")
@RequestMapping("/admin")
public class AdminController {

    private final EmployeeService employeeService;
    private final RoleService roleService;

    public AdminController(EmployeeService employeeService, RoleService roleService) {
        this.employeeService = employeeService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showAllEmployees(Model model) {
        model.addAttribute("allEmps", employeeService.getAllEmployees());
        return "all-employees";
    }

    @GetMapping("/addNewEmployee")
    public String addNewEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("roles", roleService.getAllRoles());
        return "employee-info";
    }

    @PostMapping(value = "/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") Employee employee,
                               @RequestParam(value = "nameRole", required = false) String nameRole) {
        employee.setRoles(roleService.getByName(nameRole));
        employeeService.saveEmployee(employee);
        return "redirect:/admin";
    }

    @GetMapping(value = "/updateInfo")
    public String updateEmployee(@RequestParam("empID") int id, Model model) {
        if(employeeService.getEmployee(id) == null){
            return "redirect:/admin";
        }
        model.addAttribute("roles", roleService.getAllRoles());
        model.addAttribute("employee", employeeService.getEmployee(id));
        return "employee-info";
    }

    @GetMapping("/deleteEmployee")
    public String deleteEmployee(@RequestParam("empID") int id) {
        if(employeeService.getEmployee(id) == null){
            return "redirect:/admin";
        }
        employeeService.deleteEmployee(id);
        return "redirect:/admin";
    }
}
