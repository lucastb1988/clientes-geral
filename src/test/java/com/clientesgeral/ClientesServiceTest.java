package com.clientesgeral;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.clientesgeral.domain.Cliente;
import com.clientesgeral.domain.enums.TipoClienteEnum;
import com.clientesgeral.exception.ObjectNotFoundException;
import com.clientesgeral.repositories.ClienteRepository;
import com.clientesgeral.services.ClienteService;
import com.google.common.collect.Lists;

public class ClientesServiceTest {

    @InjectMocks
    protected ClienteService clienteService = new ClienteService();

    @Mock
    protected ClienteRepository clienteRepository;

    @Rule
    public final ExpectedException excecaoEsperada = ExpectedException.none();

    public ClientesServiceTest() {
        MockitoAnnotations.initMocks(this);
	}
    
    private Cliente getClienteMock() {
    	return new Cliente(1, "Lucas Banin", "lucas@gmail.com", "38941569885", TipoClienteEnum.PESSOA_FISICA, LocalDate.of(1988, 7, 4));
    }
    
    private Cliente getClienteMock2() {
    	return new Cliente(2, "Gustavo Banin", "gustavo@gmail.com", "45263198506", TipoClienteEnum.PESSOA_FISICA, LocalDate.of(1991, 7, 2));
    }

    @Test
    public void deveBuscarClientePorId() {
    	Cliente cliente = getClienteMock();
    	when(clienteRepository.findById(anyInt())).thenReturn(Optional.of(cliente));
        cliente = clienteService.findOne(1);
        assertNotNull(cliente);
    }
    
    @Test
    public void deveBuscarClientePorEmail() {
    	Cliente cliente = getClienteMock();
    	when(clienteRepository.findByEmail(anyString())).thenReturn(cliente);
        cliente = clienteService.findByEmail("lucas@gmail.com");
        assertNotNull(cliente);
    }
    
    @Test
    public void naoDeveBuscarClientePorEmailPoisExcecaoFoiLancada() {
    	excecaoEsperada.expect(ObjectNotFoundException.class);
        when(clienteRepository.findByEmail(anyString())).thenReturn(null);
        clienteService.findByEmail("lucastttt@gmail.com");
    }

    @Test
    public void deveListarClientesPaginado() {
    	List<Cliente> lista = Lists.newArrayList(getClienteMock(), getClienteMock2());
    	when(clienteRepository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(lista));
    	Page<Cliente> clientes = clienteService.findAllPerPage(1, 5, "id", "ASC");
    	assertEquals(2, clientes.getSize());
    }

}
