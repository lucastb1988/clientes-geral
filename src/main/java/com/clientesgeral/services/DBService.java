package com.clientesgeral.services;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clientesgeral.domain.Cliente;
import com.clientesgeral.domain.enums.TipoClienteEnum;
import com.clientesgeral.repositories.ClienteRepository;

@Service
public class DBService {

	@Autowired
	private ClienteRepository clienteRepository;

	public void instantiateTestDataBase() throws ParseException {

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria.silva@gmail.com", "36378912377",
				TipoClienteEnum.PESSOA_FISICA, LocalDate.of(1955, 1, 10));

		Cliente cli2 = new Cliente(null, "Lucas Tartarini", "tartarinibanin@gmail.com", "38941569885",
				TipoClienteEnum.PESSOA_FISICA, LocalDate.of(1988, 7, 4));

		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
	}

}
