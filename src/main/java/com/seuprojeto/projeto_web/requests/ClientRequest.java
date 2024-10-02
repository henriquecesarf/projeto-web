package com.seuprojeto.projeto_web.requests;

import java.time.LocalDate;

import com.seuprojeto.projeto_web.enums.CnhCategory;
import com.seuprojeto.projeto_web.enums.Sexo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientRequest {

    private String name;
    private String surname;
    private String cpf;
    private String email;
    private Sexo sexo;
    private LocalDate dtNascimento;
    private String cnh;
    private CnhCategory cnhCategory;
    private LocalDate cnhDtMaturity;
    private String cep;
    private String address;
    private String country;
    private String city;
    private String state;
    private String complement;

    public ClientRequest() {}

    public ClientRequest(String name, String surname, String cpf, String email, Sexo sexo, LocalDate dtNascimento,
            String cnh, CnhCategory cnhCategory, LocalDate cnhDtMaturity, String cep, String address, String country,
            String city, String state, String complement) {
        this.name = name;
        this.surname = surname;
        this.cpf = cpf;
        this.email = email;
        this.sexo = sexo;
        this.dtNascimento = dtNascimento;
        this.cnh = cnh;
        this.cnhCategory = cnhCategory;
        this.cnhDtMaturity = cnhDtMaturity;
        this.cep = cep;
        this.address = address;
        this.country = country;
        this.city = city;
        this.state = state;
        this.complement = complement;
    }

}
