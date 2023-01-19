package com.unisoma.Unisoma.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "TB_EMPLOYEE")
@Getter @Setter
public class Employee implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID employeId;
    @Column(nullable = false,length = 120)
    private String name;
    @Column(nullable = false,length = 14)
    private String cpf;
    @Column(nullable = false,length = 10)
    @JsonFormat(pattern ="dd/MM/yyyy")
    private Date birthDate;
    @Column(nullable = false,length = 11)
    private String phone;
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "employee_address_address_id")
    private Address employeeAddress;
    @Column(nullable = false)
    private double salary;

    public Employee() {
    }



    public Employee(UUID employeId, String name, String cpf, Date birthDate, String phone, Address employeeAddress, double salary) {
        this.employeId = employeId;
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.phone = phone;
        this.employeeAddress = employeeAddress;
        this.salary = salary;
    }
}
