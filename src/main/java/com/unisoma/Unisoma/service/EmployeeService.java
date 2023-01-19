package com.unisoma.Unisoma.service;

import com.unisoma.Unisoma.models.Employee;
import com.unisoma.Unisoma.models.dtos.EmployeeNewSalaryDTO;
import com.unisoma.Unisoma.models.dtos.EmployeeTaxDTO;

public interface EmployeeService {

    public String insertEmployee(Employee employee);
    public boolean verifySameCpf(String cpf);
    public EmployeeNewSalaryDTO updateSalary(String cpf);
    public Employee verifyExistsEmployeeByCpf(String cpf);
    public EmployeeNewSalaryDTO calcNewSalary(EmployeeNewSalaryDTO employeeDTO);
    public EmployeeTaxDTO incomeTax(String cpf);
    public EmployeeTaxDTO calcTax(Employee employee);
    public void deleteEmployee(String cpf);
}
