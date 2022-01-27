package com.clientesgeral;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.clientesgeral.services.DBService;

@SpringBootApplication
@EnableCaching
public class ClientesGeralApplication implements CommandLineRunner {
	
	@Autowired
	private DBService dbService;
	
	public static void main(String[] args) {
		SpringApplication.run(ClientesGeralApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		dbService.instantiateTestDataBase();
	}

}
