package com.mcnz.spring.soap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import io.github.cdimascio.dotenv.Dotenv;

@EnableJpaRepositories(basePackages = "com.mcnz.spring.soap.repository")
@EntityScan(basePackages = "com.mcnz.jee.soap")
@SpringBootApplication
public class SoapBootApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();
		System.setProperty("DATABASE_HOST", dotenv.get("DATABASE_HOST"));
		System.setProperty("DATABASE_PORT", dotenv.get("DATABASE_PORT"));
		System.setProperty("DATABASE_NAME", dotenv.get("DATABASE_NAME"));
		System.setProperty("DATABASE_USERNAME", dotenv.get("DATABASE_USERNAME"));
		System.setProperty("DATABASE_PASSWORD", dotenv.get("DATABASE_PASSWORD"));
		SpringApplication.run(SoapBootApplication.class, args);
	}

}
