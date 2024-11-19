package com.seuprojeto.projeto_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
@Slf4j
public class ProjetoWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoWebApplication.class, args);
		log.info("Aplicação iniciada");
	}

}
