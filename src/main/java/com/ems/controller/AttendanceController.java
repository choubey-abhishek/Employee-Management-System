package com.ems.controller;

import com.ems.entity.Attendance;
import com.ems.entity.Employee;
import com.ems.service.AttendanceService;
import com.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EmployeeService employeeService;

    // Admin views
    @GetMapping("/admin/attendance")
    public String listAllAttendance(Model model) {
        model.addAttribute("attendances", attendanceService.getAllAttendance());
        return "admin/attendance-list";
    }

    @GetMapping("/admin/attendance/new")
    public String createAttendanceForm(Model model) {
        model.addAttribute("attendance", new Attendance());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "admin/attendance-form";
    }

    @PostMapping("/admin/attendance/save")
    public String saveAttendance(@ModelAttribute Attendance attendance) {
        attendance.setDate(LocalDate.now()); // default today
        attendanceService.saveAttendance(attendance);
        return "redirect:/admin/attendance";
    }

    // Employee views
    @GetMapping("/employee/attendance")
    public String myAttendance(Authentication authentication, Model model) {
        String username = authentication.getName();
        Employee employee = employeeService.getCurrentEmployee(username);
        model.addAttribute("attendances", attendanceService.getAttendanceByEmployee(employee));
        return "employee/my-attendance";
    }
}
