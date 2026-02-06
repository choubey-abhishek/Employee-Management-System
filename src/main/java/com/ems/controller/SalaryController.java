package com.ems.controller;

import com.ems.entity.Employee;
import com.ems.entity.Salary;
import com.ems.service.EmployeeService;
import com.ems.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/admin/salary")
    public String listAllSalary(Model model) {
        model.addAttribute("salaries", salaryService.getAllSalaries());
        return "admin/salary-list";
    }

    @GetMapping("/admin/salary/new")
    public String createSalaryForm(Model model) {
        model.addAttribute("salary", new Salary());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "admin/salary-form";
    }

    @PostMapping("/admin/salary/save")
    public String saveSalary(@ModelAttribute Salary salary) {
        salaryService.saveSalary(salary);
        return "redirect:/admin/salary";
    }

    @GetMapping("/employee/salary")
    public String mySalary(Authentication authentication, Model model) {
        String username = authentication.getName();
        Employee employee = employeeService.getCurrentEmployee(username);
        model.addAttribute("salaries", salaryService.getSalaryByEmployee(employee));
        return "employee/my-salary";
    }
}
