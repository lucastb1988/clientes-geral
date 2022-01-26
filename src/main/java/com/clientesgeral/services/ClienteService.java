package com.clientesgeral.services;

import java.util.List;
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
import com.clientesgeral.infrastructure.cache.CacheConfigurationProperties;
import com.clientesgeral.repositories.ClienteRepository;

@Service
public class ClienteService {
	
    @Autowired
    private ClienteRepository repo;
    
    //@Cacheable(cacheNames = CacheConfigurationProperties.BUSCAR_POR_ID)
    public Cliente findOne(Integer id) {
    	Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }
    
    //@Cacheable(cacheNames = CacheConfigurationProperties.BUSCAR_POR_EMAIL)
    public Cliente findByEmail(String email) {
    	Cliente obj = repo.findByEmail(email);
    	if (obj == null) {
    		throw new ObjectNotFoundException("Objeto não encontrado! Email: " + email
    				+ ", Tipo: " + Cliente.class.getName());
    	}
    	return obj;
    }
    
    @Transactional
    public Cliente insert(Cliente obj) {
    	obj.setId(null);
    	obj = repo.save(obj);
    	return obj;
    }
    
    @Transactional
    public Cliente update(Cliente obj) {
    	Cliente newObj = findOne(obj.getId());
    	updateData(newObj, obj);
    	return repo.save(newObj);
    }
    
    private void updateData(Cliente newObj, Cliente obj) {
    	newObj.setNome(obj.getNome());
    	newObj.setEmail(obj.getEmail());
    	newObj.setCpfOuCnpj(obj.getCpfOuCnpj());
    	newObj.setTipo(obj.getTipo());
    	newObj.setDataNascimento(obj.getDataNascimento());
    }
    
    //@Cacheable(cacheNames = CacheConfigurationProperties.BUSCAR_TODOS)
    public List<Cliente> findAll() {
    	return repo.findAll();
    }
    
    //@Cacheable(cacheNames = CacheConfigurationProperties.BUSCAR_PAGINADO)
    public Page<Cliente> findAllPerPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
    	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    	return repo.findAll(pageRequest);
    }
    
}
