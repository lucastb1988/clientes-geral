package com.clientesgeral.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.clientesgeral.domain.Cliente;
import com.clientesgeral.services.validation.ClienteUpdate;
import com.fasterxml.jackson.annotation.JsonFormat;

@ClienteUpdate
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = -9031487931907790020L;

	private Integer id;

	@Length(min = 5, max = 50, message = "O tamanho deve ser entre 5 e 50 caracteres")
	private String nome;

	@Email(message = "E-mail inválido")
	private String email;

	private String cpfOuCnpj;

	private Integer tipo;

	@JsonFormat(pattern = "dd-MM-yyyy", locale = "pt_BR")
    @DateTimeFormat(pattern = "dd-MM-yyyy", iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataNascimento;
	
	private Integer idade;

	public ClienteDTO() {
		super();
	}

	public ClienteDTO(Cliente obj) {
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
		cpfOuCnpj = obj.getCpfOuCnpj();
		tipo = obj.getTipo();
		dataNascimento = obj.getDataNascimento();
		idade = getIdadeCalculada(dataNascimento);
	}
	
	private Integer getIdadeCalculada(LocalDate dataNascimento) {
		if (dataNascimento == null) {
			return 0;
		}
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
