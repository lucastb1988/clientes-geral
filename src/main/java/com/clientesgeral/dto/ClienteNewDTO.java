package com.clientesgeral.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.clientesgeral.domain.Cliente;
import com.clientesgeral.services.validation.ClienteInsert;
import com.fasterxml.jackson.annotation.JsonFormat;

@ClienteInsert
public class ClienteNewDTO implements Serializable {

	private static final long serialVersionUID = -9031487931907790020L;

	private Integer id;

	@NotNull(message = "Preenchimento Obrigatório")
	@Length(min = 5, max = 50, message = "O tamanho deve ser entre 5 e 50 caracteres")
	private String nome;

	@NotNull(message = "Preenchimento Obrigatório")
	@Email(message = "E-mail inválido")
	private String email;

	@NotNull(message = "Preenchimento Obrigatório")
	private String cpfOuCnpj;

	@NotNull(message = "Preenchimento Obrigatório")
	private Integer tipo;

	@NotNull(message = "Preenchimento Obrigatório")
	@JsonFormat(pattern = "dd-MM-yyyy", locale = "pt_BR")
    @DateTimeFormat(pattern = "dd-MM-yyyy", iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataNascimento;
	
	private Integer idade;

	public ClienteNewDTO() {
		super();
	}

	public ClienteNewDTO(Cliente obj) {
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
		cpfOuCnpj = obj.getCpfOuCnpj();
		tipo = obj.getTipo();
		dataNascimento = obj.getDataNascimento();
		idade = getIdadeCalculada(dataNascimento);
	}
	
	private Integer getIdadeCalculada(LocalDate dataNascimento) {
		final LocalDate dataAtual = LocalDate.now();
	    final Period periodo = Period.between(dataNascimento, dataAtual);
		return periodo.getYears();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}
	
}
