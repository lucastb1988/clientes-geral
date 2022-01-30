package com.clientesgeral.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clientesgeral.domain.Cliente;
import com.clientesgeral.exception.ObjectNotFoundException;
import com.clientesgeral.repositories.ClienteRepository;
import com.clientesgeral.services.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Cacheable("id")
	@Override
	public Cliente buscarPorId(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Cacheable("email")
	@Override
	public Cliente buscarPorEmail(String email) {
		Cliente obj = clienteRepository.buscarPorEmail(email);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Email: " + email + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}

	@Transactional
	@Override
	public Cliente salvar(Cliente obj) {
		obj.setId(null);
		obj = clienteRepository.save(obj);
		return obj;
	}

	@Transactional
	@Override
	public Cliente atualizar(Cliente obj) {
		Cliente newObj = buscarPorId(obj.getId());
		atualizarData(newObj, obj);
		return clienteRepository.save(newObj);
	}

	private void atualizarData(Cliente newObj, Cliente obj) {
		if (obj.getNome() != null) {
			newObj.setNome(obj.getNome());
		}
		if (obj.getEmail() != null) {
			newObj.setEmail(obj.getEmail());
		}
		if (obj.getCpfOuCnpj() != null) {
			newObj.setCpfOuCnpj(obj.getCpfOuCnpj());
		}
		if (obj.getTipo() != null) {
			newObj.setTipo(obj.getTipo());
		}
		if (obj.getDataNascimento() != null) {
			newObj.setDataNascimento(obj.getDataNascimento());
		}
	}

	@Cacheable("page")
	@Override
	public Page<Cliente> buscarPaginado(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}

}
