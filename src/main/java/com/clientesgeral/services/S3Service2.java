package com.clientesgeral.services;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service2 {
	
	private Logger LOG = LoggerFactory.getLogger(S3Service2.class);

	@Autowired
	private AmazonS3 s3client;
	
	@Value("${s3.bucket}")
	private String bucketName;

	// método responsável para fazer um upload no AWS S3
	public void uploadFile(String localFile) {
		try {
			File file = new File(localFile);
			LOG.info("Iniciando upload");
			s3client.putObject(new PutObjectRequest(bucketName, "teste.jpeg", file));
			LOG.info("Upload finalizado");
		} catch (Exception e) {
			LOG.error("Erro");
		}
	}
}
