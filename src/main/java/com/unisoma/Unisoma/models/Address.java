package com.unisoma.Unisoma.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "TB_ADDRESS")
@Getter @Setter
public class Address implements Serializable {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID addressId;
    @Column(nullable = false,length = 120)
    private String logradouro;
    @Column(length = 35)
    private String bairro;
    @Column(nullable = false,length = 9)
    private String cep;
    @Column(nullable = false,length = 6)
    private Integer number;
    @Column(nullable = false,length = 40)
    private String  city;
    @Column(nullable = false,length = 40)
    private String state;

    public Address() {
    }

    public Address(String logradouro, String bairro, String cep, Integer number, String city, String state) {
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cep = cep;
        this.number = number;
        this.city = city;
        this.state = state;
    }

    public Address(UUID addressId, String logradouro, String bairro, String cep, Integer number, String city, String state) {
        this.addressId = addressId;
        this.logradouro = logradouro;
        this.bairro = bairro;
        this.cep = cep;
        this.number = number;
        this.city = city;
        this.state = state;
    }
}
