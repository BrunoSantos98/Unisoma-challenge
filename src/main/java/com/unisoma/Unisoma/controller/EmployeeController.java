package com.unisoma.Unisoma.controller;

import com.unisoma.Unisoma.models.Employee;
import com.unisoma.Unisoma.models.dtos.EmployeeNewSalaryDTO;
import com.unisoma.Unisoma.models.dtos.EmployeeTaxDTO;
import com.unisoma.Unisoma.service.implementations.Employeeimplementations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "*",maxAge = 3600)
public class EmployeeController {

    @Autowired
    Employeeimplementations employeeimplementations;

    @PostMapping
    public ResponseEntity<String> employeeRegister(@RequestBody Employee employee){
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeimplementations.insertEmployee(employee));
    }

    @PatchMapping
    public ResponseEntity<EmployeeNewSalaryDTO> updateEmployeeSalary(@RequestParam String cpf){
        return ResponseEntity.ok(employeeimplementations.updateSalary(cpf));
    }

    @GetMapping
    public ResponseEntity<EmployeeTaxDTO> verifyTax(@RequestParam String cpf){
        return ResponseEntity.ok(employeeimplementations.incomeTax(cpf));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteEmployee(@RequestParam String cpf){
        employeeimplementations.deleteEmployee(cpf);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Funcionario deletado com sucesso");
    }
}
