package com.unisoma.Unisoma.repository;

import com.unisoma.Unisoma.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,UUID> {
    boolean existsByCpf(String cpf);
    Employee findByCpf(String cpf);

}
