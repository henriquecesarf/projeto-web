package com.seuprojeto.projeto_web.requests;

import java.time.LocalDate;

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

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotBlank(message = "Sobrenome é obrigatório")
    private String surname;

    @NotBlank(message = "CPF é obrigatório")
    @CPF(message = "CPF deve ser válido")
    private String cpf;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotNull(message = "Sexo é obrigatório")
    private Sexo sexo;
    
    @NotNull(message = "Data Nascimento é obrigatório")
    private LocalDate dtNascimento;

    @NotBlank(message = "CNH é obrigatório")
    private String cnh;

    @NotNull(message = "Categoria CNH é obrigatório")
    private CnhCategory cnhCategory;

    @NotNull(message = "Data Vencimento CNH é obrigatório")
    private LocalDate cnhDtMaturity;

    @NotBlank(message = "CEP é obrigatório")
    private String cep;

    @NotBlank(message = "Endereço é obrigatório")
    private String address;

    @NotBlank(message = "Pais é obrigatório")
    private String country;

    @NotBlank(message = "Cidade é obrigatório")
    private String city;

    @NotBlank(message = "Estado é obrigatório")
    private String state;

    @NotBlank(message = "Complemento é obrigatório")
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

    @Override
    public String toString() {
        return "[name=" + name + ", surname=" + surname + ", cpf=" + cpf + ", email=" + email + ", sexo="
                + sexo + ", dtNascimento=" + dtNascimento + ", cnh=" + cnh + ", cnhCategory=" + cnhCategory
                + ", cnhDtMaturity=" + cnhDtMaturity + ", cep=" + cep + ", address=" + address + ", country=" + country
                + ", city=" + city + ", state=" + state + ", complement=" + complement + "]";
    }

    

}
