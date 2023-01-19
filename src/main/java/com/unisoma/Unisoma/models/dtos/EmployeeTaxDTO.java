package com.unisoma.Unisoma.models.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EmployeeTaxDTO {

    @JsonProperty("CPF")
    private String cpf;
    @JsonProperty("Imposto")
    private String tax;


    public EmployeeTaxDTO(String cpf, String tax){
        this.cpf = cpf;
        this.tax = tax;
    }

    public EmployeeTaxDTO() {

    }
}
