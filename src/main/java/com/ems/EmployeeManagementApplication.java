package com.ems;

import com.ems.entity.Employee;
import com.ems.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EmployeeManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(EmployeeRepository employeeRepository,
                                      BCryptPasswordEncoder passwordEncoder) {
        return args -> {
            if (employeeRepository.findByUsername("admin").isEmpty()) {
                Employee admin = new Employee();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setName("Administrator");
                admin.setEmail("admin@company.com");
                admin.setDepartment("IT");
                admin.setDesignation("Admin");
                admin.setRole("ROLE_ADMIN");
                employeeRepository.save(admin);
            }
        };
    }
}
