package com.unisoma.Unisoma.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unisoma.Unisoma.exceptions.CpfRegisteredException;
import com.unisoma.Unisoma.models.Address;
import com.unisoma.Unisoma.models.Employee;
import com.unisoma.Unisoma.models.dtos.EmployeeNewSalaryDTO;
import com.unisoma.Unisoma.models.dtos.EmployeeTaxDTO;
import com.unisoma.Unisoma.service.implementations.Employeeimplementations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.Date;
import java.util.UUID;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest{

    @MockBean
    private Employeeimplementations implementations;
    @InjectMocks
    private EmployeeController controller;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    String sp = "Sao Paulo";
    Address address = new Address(UUID.randomUUID(),"pracinha da se","se","01001010",14,sp,sp);
    Employee employee = new Employee(UUID.randomUUID(),"Fabricio de moura santos","025.364.247-98",new Date(),"91952365478",address,3002.00);
    EmployeeNewSalaryDTO empSalaryDto = new EmployeeNewSalaryDTO("025.364.247-98",3072.00,70.00,4);
    EmployeeTaxDTO empTaxDTO = new EmployeeTaxDTO("025.364.247-98","18");

    @Test
    @DisplayName("Salvando novo usuario")
    void shouldBeInsertNewEmployee() throws Exception {
        given(implementations.insertEmployee(any(Employee.class))).willReturn("Funcionario cadastrado com sucesso");

        mockMvc.perform(post("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("Atualizar salario")
    void updateEmployeeSalary() throws Exception{
        given(implementations.updateSalary(employee.getCpf())).willReturn(empSalaryDto);

        mockMvc.perform(patch("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("cpf",employee.getCpf()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Verificar Taxa de imposto")
    void verifyTax() throws Exception {
        given(implementations.incomeTax(employee.getCpf())).willReturn(empTaxDTO);

        mockMvc.perform(get("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("cpf",employee.getCpf()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteEmployee() throws Exception {

        mockMvc.perform(delete("/api/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("cpf",employee.getCpf()))
                .andExpect(status().isAccepted())
                .andDo(print());
    }
}