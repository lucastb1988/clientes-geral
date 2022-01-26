package com.clientesgeral.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.clientesgeral.domain.Cliente;
import com.clientesgeral.dto.ClienteDTO;
import com.clientesgeral.dto.ClienteNewDTO;
import com.clientesgeral.services.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = {"API dedicada a atender as necessidades dos processos de cliente."})
@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {
	
	private Logger LOG = LoggerFactory.getLogger(ClienteResource.class);

	@Autowired
	private ClienteService service;

	@ApiOperation("Buscar cliente por id.")
	@GetMapping("/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
		Cliente obj = service.findOne(id);
		return ResponseEntity.ok().body(new ClienteDTO(obj));
	}
	
	@ApiOperation("Buscar cliente por email.")
	@GetMapping("/email")
	public ResponseEntity<ClienteDTO> findByEmail(@RequestParam(required = true) String email) {
		Cliente obj = service.findByEmail(email);
		LOG.info("Consegui bater");
		return ResponseEntity.ok().body(new ClienteDTO(obj));
	}
	
	@ApiOperation("Salvar cliente.")
	@PostMapping
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {
		Cliente obj = new Cliente();
        BeanUtils.copyProperties(objDto, obj);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri(); 
		return ResponseEntity.created(uri).build();
	}
	
	@ApiOperation("Atualizar cliente.")
	@PutMapping("/{id}")
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
		Cliente obj = new Cliente();
        BeanUtils.copyProperties(objDto, obj);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation("Buscar todos os clientes inseridos no banco.")
	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = service.findAll();
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		LOG.info("Consegui bater");
		return ResponseEntity.ok().body(listDto);
	}
	
	@ApiOperation("Buscar os clientes inseridos no banco de forma paginada.")
	@GetMapping("/page")
	public ResponseEntity<Page<ClienteDTO>> findAllPerPage(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Cliente> list = service.findAllPerPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
		LOG.info("Consegui bater");
		return ResponseEntity.ok().body(listDto);
	}
}
