package com.unisoma.Unisoma.service;

import com.unisoma.Unisoma.exceptions.CpfRegisteredException;
import com.unisoma.Unisoma.exceptions.EntityNotFoundException;
import com.unisoma.Unisoma.models.Address;
import com.unisoma.Unisoma.models.Employee;
import com.unisoma.Unisoma.models.dtos.EmployeeNewSalaryDTO;
import com.unisoma.Unisoma.models.dtos.EmployeeTaxDTO;
import com.unisoma.Unisoma.repository.EmployeeRepository;
import com.unisoma.Unisoma.service.implementations.Employeeimplementations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeimplementationsTest {

    @Mock
    EmployeeRepository repository;
    @InjectMocks
    Employeeimplementations implementations;

    String sp = "São Paulo";
    Address address = new Address(UUID.randomUUID(),"pracinha da se","se","01001010",14,sp,sp);
    Employee employee = new Employee(UUID.randomUUID(),"Fabricio de moura santos","025.364.247-98",new Date(),"91952365478",address,3002.00);
    EmployeeNewSalaryDTO empDTO = new EmployeeNewSalaryDTO("025.364.247-88",5000.00,0.00,0);
    EmployeeTaxDTO empTaxDto = new EmployeeTaxDTO();

    @Test
    @DisplayName("Salva novo funcionario")
    void ShouldBeInsertEmployee() {
        given(repository.existsByCpf(employee.getCpf())).willReturn(false);
        given(repository.save(any(Employee.class))).willReturn(employee);

        String response = implementations.insertEmployee(employee);

        verify(repository,times(1)).existsByCpf(employee.getCpf());
        verify(repository,times(1)).save(employee);
        assertEquals("Funcionario cadastrado com sucesso",response);
    }

    @Test
    @DisplayName("Lanca excessao de cpf ja existente na base de dados")
    void shouldNotInsertEmployeByDuplicateCpf(){
        given(repository.existsByCpf(employee.getCpf())).willReturn(true);

        assertThrows(CpfRegisteredException.class,() -> implementations.insertEmployee(employee));
        verify(repository,times(1)).existsByCpf(employee.getCpf());
    }

    @Test
    @DisplayName("verifica se o cpf existe na base de dados e retorna true")
    void shouldBeVerifySameCpfAndResponseTrue() {
        given(repository.existsByCpf(employee.getCpf())).willReturn(true);

        boolean response = implementations.verifySameCpf(employee.getCpf());

        verify(repository,times(1)).existsByCpf(employee.getCpf());
        assertEquals(true,response);
    }

    @Test
    @DisplayName("Verifica se o cpf existe na base de dados e retorna false")
    void shouldBeVerifySameCpfAndResponseFalse(){
        given(repository.existsByCpf(employee.getCpf())).willReturn(false);

        boolean response = implementations.verifySameCpf(employee.getCpf());

        verify(repository,times(1)).existsByCpf(employee.getCpf());
        assertEquals(false,response);
    }

    @Test
    @DisplayName("Atualiza o salario")
    void ShouldBeUpdateSalary() {
        given(repository.existsByCpf(employee.getCpf())).willReturn(true);
        given(repository.findByCpf(employee.getCpf())).willReturn(employee);
        given(repository.save(any(Employee.class))).willReturn(employee);

        EmployeeNewSalaryDTO empDTO = implementations.updateSalary(employee.getCpf());

        verify(repository,times(1)).existsByCpf(employee.getCpf());
        verify(repository,times(1)).findByCpf(employee.getCpf());
        verify(repository,times(1)).save(employee);
        assertEquals(3122.08,empDTO.getSalary());
    }

    @Test
    @DisplayName("Testa se o CPF existe na base de dados e retorna um funcionario")
    void ShouldBeVerifyExistsEmployeeByCpfIsTrue() {
        given(repository.existsByCpf(employee.getCpf())).willReturn(true);
        given(repository.findByCpf(employee.getCpf())).willReturn(employee);

        employee = implementations.verifyExistsEmployeeByCpf(employee.getCpf());

        verify(repository,times(1)).existsByCpf(employee.getCpf());
        verify(repository,times(1)).findByCpf(employee.getCpf());
    }

    @Test
    @DisplayName("Verifica se o cpf existe na base de dados e retorna uma excecao")
    void ShouldBeVerifyExistsEmployeeByCpfIsFalse() {
        given(repository.existsByCpf(employee.getCpf())).willReturn(false);

        assertThrows(EntityNotFoundException.class,() -> implementations.verifyExistsEmployeeByCpf(employee.getCpf()));

        verify(repository,times(1)).existsByCpf(employee.getCpf());

    }

    @Test
    @DisplayName("AUmenta salario 4%")
    void ShouldBeIncreaseSalaryFourPercent() {
        empDTO = implementations.calcNewSalary(empDTO);

        assertEquals(5200.00,empDTO.getSalary());
    }

    @Test
    @DisplayName("AUmenta salario 7%")
    void ShouldBeIncreaseSalarySevenPercecnt() {
        empDTO.setSalary(1500.00);
        empDTO = implementations.calcNewSalary(empDTO);

        assertEquals(1605.00,empDTO.getSalary());
    }

    @Test
    @DisplayName("AUmenta salario 10%")
    void ShouldBeIncreaseSalaryTenPercecnt() {
        empDTO.setSalary(1200.00);
        empDTO = implementations.calcNewSalary(empDTO);

        assertEquals(1320.00,empDTO.getSalary());
    }

    @Test
    @DisplayName("AUmenta salario 12%")
    void ShouldBeIncreaseSalaryTwelvePercecnt() {
        empDTO.setSalary(500.00);
        empDTO = implementations.calcNewSalary(empDTO);

        assertEquals(560.00,empDTO.getSalary());
    }

    @Test
    @DisplayName("AUmenta salario 15%")
    void ShouldBeIncreaseSalaryFifteenPercecnt() {
        empDTO.setSalary(100.00);
        empDTO = implementations.calcNewSalary(empDTO);

        assertEquals(115.00,empDTO.getSalary());
    }

    @Test
    @DisplayName("Faz a devolucao do quantro de imposto que tem que pagar")
    void ShouldBeIncomeTax() {
        given(repository.existsByCpf(employee.getCpf())).willReturn(true);
        given(repository.findByCpf(employee.getCpf())).willReturn(employee);

        empTaxDto = implementations.incomeTax(employee.getCpf());

        verify(repository,times(1)).existsByCpf(employee.getCpf());
        verify(repository,times(1)).findByCpf(employee.getCpf());
        assertEquals("Imposto: R$ 80,36",empTaxDto.getTax());
    }

    @Test
    @DisplayName("Verifica O imposto e retorna isento")
    void ShouldBeIsentTax() {
        employee.setSalary(2000.00);
        empTaxDto = implementations.calcTax(employee);

        assertEquals("Isento",empTaxDto.getTax());
    }

    @Test
    @DisplayName("Verifica O imposto e retorna 8% de taxa")
    void ShouldBeEightPercentTax() {
        employee.setSalary(2900.00);
        empTaxDto = implementations.calcTax(employee);

        assertEquals("Imposto: R$ 72,00",empTaxDto.getTax());
    }

    @Test
    @DisplayName("Verifica O imposto e retorna 18% de taxa")
    void ShouldBeEighteenPercentTax() {
        employee.setSalary(4328.60);
        empTaxDto = implementations.calcTax(employee);

        assertEquals("Imposto: R$ 319,15",empTaxDto.getTax());
    }

    @Test
    @DisplayName("Verifica O imposto e retorna 28% de taxa")
    void ShouldBeTwentyEightPercentTax() {
        employee.setSalary(5000.00);
        empTaxDto = implementations.calcTax(employee);

        assertEquals("Imposto: R$ 490,00",empTaxDto.getTax());
    }

    @Test
    @DisplayName("Deleta um funcionario da base de dados")
    void deleteEmployee() {
        given(repository.existsByCpf(employee.getCpf())).willReturn(true);
        given(repository.findByCpf(employee.getCpf())).willReturn(employee);

        implementations.deleteEmployee(employee.getCpf());

        verify(repository,times(1)).existsByCpf(employee.getCpf());
        verify(repository,times(1)).findByCpf(employee.getCpf());
        verify(repository,times(1)).delete(employee);
    }
}