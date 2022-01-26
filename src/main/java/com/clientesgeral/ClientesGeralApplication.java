package com.clientesgeral;

import java.io.File;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import com.clientesgeral.services.DBService;
import com.clientesgeral.services.S3Service2;

@SpringBootApplication
@EnableCaching
public class ClientesGeralApplication implements CommandLineRunner {
	
	@Autowired
	private DBService dbService;
	
	@Autowired
	private S3Service2 s3Service;

	public static void main(String[] args) {
		SpringApplication.run(ClientesGeralApplication.class, args);
	}
	
	@Override
	public void run(String... args) throws Exception {
		dbService.instantiateTestDataBase();
		
		final String dir = Paths.get("").toFile().getAbsolutePath();
        File f = new File(dir + "\\img\\coruja.jpeg");
		s3Service.uploadFile(f.getAbsolutePath());
	}

}
