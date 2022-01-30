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
		Cliente novoObj = buscarPorId(obj.getId());
		atualizarData(novoObj, obj);
		return clienteRepository.save(novoObj);
	}

	private void atualizarData(Cliente novoObj, Cliente obj) {
		if (obj.getNome() != null) {
			novoObj.setNome(obj.getNome());
		}
		if (obj.getEmail() != null) {
			novoObj.setEmail(obj.getEmail());
		}
		if (obj.getCpfOuCnpj() != null) {
			novoObj.setCpfOuCnpj(obj.getCpfOuCnpj());
		}
		if (obj.getTipo() != null) {
			novoObj.setTipo(obj.getTipo());
		}
		if (obj.getDataNascimento() != null) {
			novoObj.setDataNascimento(obj.getDataNascimento());
		}
	}

	@Cacheable("page")
	@Override
	public Page<Cliente> buscarPaginado(Integer pagina, Integer qtdePorPagina, String ordenacaoPor, String direcao) {
		PageRequest pageRequest = PageRequest.of(pagina, qtdePorPagina, Direction.valueOf(direcao), ordenacaoPor);
		return clienteRepository.findAll(pageRequest);
	}

}
