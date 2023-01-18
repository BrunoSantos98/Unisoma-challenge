package com.unisoma.Unisoma.models;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Data
public class EmployeeNewSalaryDTO {

    private Long cpf;
    private Double salary;
    private double readjustment;
    private int percent;

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

}
