package com.seuprojeto.projeto_web.requests;

import java.time.LocalDate;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.seuprojeto.projeto_web.enums.CnhCategory;
import com.seuprojeto.projeto_web.enums.Sexo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClientRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Surname is required")
    private String surname;

    @NotBlank(message = "CPF is required")
    @CPF(message = "CPF must be valid")
    private String cpf;

    @CNPJ(message = "CNPJ must be valid")
    private String cnpj;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    private String email;

    @NotNull(message = "Gender is required")
    private Sexo sexo;

    @NotNull(message = "Date of Birth is required")
    private LocalDate dtNascimento;

    @NotBlank(message = "Driver's license is required")
    private String cnh;

    @NotNull(message = "Driver's license category is required")
    private CnhCategory cnhCategory;

    @NotNull(message = "Driver's license expiration date is required")
    private LocalDate cnhDtMaturity;

    @NotBlank(message = "Postal code is required")
    private String cep;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Complement is required")
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
