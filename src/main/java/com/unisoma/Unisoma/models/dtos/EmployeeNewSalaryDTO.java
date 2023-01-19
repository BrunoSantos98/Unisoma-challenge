package com.unisoma.Unisoma.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.unisoma.Unisoma.models.Employee;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class EmployeeNewSalaryDTO {

    @JsonProperty("CPF")
    private String cpf;
    @JsonProperty("Novo salario")
    private Double salary;
    @JsonProperty("Reajuste ganho")
    private double readjustment;
    @JsonProperty("Em percentual")
    private int percent;

    public EmployeeNewSalaryDTO() {
    }

    public EmployeeNewSalaryDTO convert(Employee employee){
        BeanUtils.copyProperties(employee,this,"employeId","name", "birthDate","phone","employeeAddress");
        return this;
    }

    public List<EmployeeNewSalaryDTO> convertList(List<Employee> listEmployee){
        EmployeeNewSalaryDTO empDTO = new EmployeeNewSalaryDTO();
        List<EmployeeNewSalaryDTO> empListDTO = new ArrayList<>();
        listEmployee.forEach(employee -> {
            empListDTO.add(empDTO.convert(employee));
        });
        return empListDTO;
    }

    public EmployeeNewSalaryDTO(String cpf, Double salary, Double readjustment, int percent) {
        this.cpf = cpf;
        this.salary = salary;
        this.readjustment = readjustment;
        this.percent = percent;
    }
}
