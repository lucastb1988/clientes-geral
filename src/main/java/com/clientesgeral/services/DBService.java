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

		Cliente cli3 = new Cliente(null, "Gustavo Tartarini", "gustavobanin@gmail.com", "38845692058",
				TipoClienteEnum.PESSOA_FISICA, LocalDate.of(1991, 7, 2));

		Cliente cli4 = new Cliente(null, "Renata Tartarini", "renatabanin@gmail.com", "40159963214",
				TipoClienteEnum.PESSOA_FISICA, LocalDate.of(1993, 8, 13));

		Cliente cli5 = new Cliente(null, "Cleuza Tartarini Banin", "cleuzabanin@gmail.com", "29379087802",
				TipoClienteEnum.PESSOA_FISICA, LocalDate.of(1965, 8, 3));

		Cliente cli6 = new Cliente(null, "Giovane Tartarini", "giovanetartarini@gmail.com", "29865931405",
				TipoClienteEnum.PESSOA_FISICA, LocalDate.of(1988, 2, 10));

		Cliente cli7 = new Cliente(null, "Vanderlei Tartarini", "vanderleibanin@gmail.com", "45567849280",
				TipoClienteEnum.PESSOA_FISICA, LocalDate.of(1985, 5, 14));

		clienteRepository.saveAll(Arrays.asList(cli1, cli2, cli4, cli5, cli6, cli7));
	}

}
