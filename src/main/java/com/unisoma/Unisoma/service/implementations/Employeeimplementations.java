package com.unisoma.Unisoma.service.implementations;

import com.unisoma.Unisoma.exceptions.CpfRegisteredException;
import com.unisoma.Unisoma.exceptions.EntityNotFoundException;
import com.unisoma.Unisoma.models.Employee;
import com.unisoma.Unisoma.models.dtos.EmployeeNewSalaryDTO;
import com.unisoma.Unisoma.models.dtos.EmployeeTaxDTO;
import com.unisoma.Unisoma.repository.EmployeeRepository;
import com.unisoma.Unisoma.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class Employeeimplementations implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public String insertEmployee(Employee employee) {
        if(verifySameCpf(employee.getCpf())){
            throw new CpfRegisteredException("Cpf  ja cadastradom na base  dee dados, por favor tente novamente com outro cpf");
        }else {
            employeeRepository.save(employee);
            return "Funcionario cadastrado com sucesso";
        }
    }

    @Override
    public boolean verifySameCpf(String cpf) {
        return employeeRepository.existsByCpf(cpf);
    }

    @Override
    public EmployeeNewSalaryDTO updateSalary(String cpf) {
            Employee employee = verifyExistsEmployeeByCpf(cpf);
            EmployeeNewSalaryDTO empSalaryDTO = new EmployeeNewSalaryDTO();

            empSalaryDTO.convert(employee);
            empSalaryDTO = calcNewSalary(empSalaryDTO);
            employee.setSalary(empSalaryDTO.getSalary());
            employeeRepository.save(employee);
            return empSalaryDTO;

    }

    @Override
    public Employee verifyExistsEmployeeByCpf(String cpf) {
        if(verifySameCpf(cpf)){
            return employeeRepository.findByCpf(cpf);
        }else{
            throw new EntityNotFoundException("Não foi encontrado nenhuma pessoa com esse CPF, por favor corrija as informções e tente novamente");
        }
    }

    @Override
    public EmployeeNewSalaryDTO calcNewSalary(EmployeeNewSalaryDTO employeeDTO) {
        Double oldSalary = employeeDTO.getSalary();
        if(oldSalary> 2000.00){
            employeeDTO.setPercent(4);
            employeeDTO.setReadjustment(oldSalary * employeeDTO.getPercent()/100);
            employeeDTO.setSalary(oldSalary + employeeDTO.getReadjustment());
        } else if(oldSalary > 1200.00){
            employeeDTO.setPercent(7);
            employeeDTO.setReadjustment(oldSalary * employeeDTO.getPercent()/100);
            employeeDTO.setSalary(oldSalary + employeeDTO.getReadjustment());
        }else if(oldSalary > 800.00){
            employeeDTO.setPercent(10);
            employeeDTO.setReadjustment(oldSalary * employeeDTO.getPercent()/100);
            employeeDTO.setSalary(oldSalary + employeeDTO.getReadjustment());
        }else if(oldSalary > 400.00){
            employeeDTO.setPercent(12);
            employeeDTO.setReadjustment(oldSalary * employeeDTO.getPercent()/100);
            employeeDTO.setSalary(oldSalary + employeeDTO.getReadjustment());
        }else{
            employeeDTO.setPercent(15);
            employeeDTO.setReadjustment(oldSalary * employeeDTO.getPercent()/100);
            employeeDTO.setSalary(oldSalary + employeeDTO.getReadjustment());
        }
        return employeeDTO;
    }

    @Override
    public EmployeeTaxDTO incomeTax(String cpf) {
        Employee employee = verifyExistsEmployeeByCpf(cpf);
        EmployeeTaxDTO empTaxDTO = calcTax(employee);
        return empTaxDTO;
    }

    @Override
    public EmployeeTaxDTO calcTax(Employee employee) {
        EmployeeTaxDTO empTaxDTO = new EmployeeTaxDTO();
        empTaxDTO.setCpf(employee.getCpf());
        double tax;
        double eigthPercent = 0.08;
        double eighteenPercent = 0.18;
        double twentyEightPercent = 0.28;

        if(employee.getSalary() > 4500.00){
            tax =  1000 * eigthPercent + 1500 * eighteenPercent + ((employee.getSalary() - 4500) * twentyEightPercent);
        }else if(employee.getSalary() > 3000.00){
            tax = 1000 * eigthPercent + ((employee.getSalary() - 3000) * eighteenPercent);
        }else if(employee.getSalary() > 2000.00){
            tax = (employee.getSalary()-2000) * eigthPercent;
        }else{
            tax = 0;
        }

        if(tax > 0){
            empTaxDTO.setTax("Imposto: R$ " + String.format("%.2f",tax));
        }else{
            empTaxDTO.setTax("Isento");
        }

        return  empTaxDTO;
    }

    @Override
    public void deleteEmployee(String cpf) {
        Employee employee = verifyExistsEmployeeByCpf(cpf);
        employeeRepository.delete(employee);
    }
}
