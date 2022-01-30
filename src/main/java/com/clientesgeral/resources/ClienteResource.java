package com.clientesgeral.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.clientesgeral.domain.Cliente;
import com.clientesgeral.dto.ClienteDTO;
import com.clientesgeral.dto.ClienteNewDTO;
import com.clientesgeral.services.ClienteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = { "API dedicada a atender as necessidades dos processos de cliente." })
@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;

	@ApiOperation("Buscar cliente por id.")
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ClienteDTO buscarPorId(@PathVariable Integer id) {
		return new ClienteDTO(service.buscarPorId(id));
	}

	@ApiOperation("Buscar cliente por email.")
	@GetMapping("/email")
	@ResponseStatus(HttpStatus.OK)
	public ClienteDTO buscarPorEmail(@RequestParam(required = true) String email) {
		return new ClienteDTO(service.buscarPorEmail(email));
	}

	@ApiOperation("Salvar cliente.")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Void> salvar(@Valid @RequestBody ClienteNewDTO objDto) {
		Cliente obj = new Cliente();
		BeanUtils.copyProperties(objDto, obj);
		obj = service.salvar(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@ApiOperation("Atualizar cliente.")
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
		Cliente obj = new Cliente();
		BeanUtils.copyProperties(objDto, obj);
		obj.setId(id);
		obj = service.atualizar(obj);
	}

	@ApiOperation("Buscar os clientes inseridos no banco de forma paginada.")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Page<ClienteDTO> buscarPaginado(@RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
			@RequestParam(value = "qtdePorPagina", defaultValue = "10") Integer qtdePorPagina,
			@RequestParam(value = "ordenacaoPor", defaultValue = "nome") String ordenacaoPor,
			@RequestParam(value = "direcao", defaultValue = "ASC") String direcao) {
		Page<Cliente> lista = service.buscarPaginado(pagina, qtdePorPagina, ordenacaoPor, direcao);
		Page<ClienteDTO> listaDto = lista.map(obj -> new ClienteDTO(obj));
		return listaDto;
	}
}
