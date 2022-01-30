package com.clientesgeral.services;

import org.springframework.data.domain.Page;

import com.clientesgeral.domain.Cliente;

public interface ClienteService {

	Cliente buscarPorId(Integer id);
	
	Cliente buscarPorEmail(String email); 

	Cliente salvar(Cliente obj);
	 
	Cliente atualizar(Cliente obj);
	 
	Page<Cliente> buscarPaginado(Integer page, Integer linesPerPage, String orderBy, String direction);

}
