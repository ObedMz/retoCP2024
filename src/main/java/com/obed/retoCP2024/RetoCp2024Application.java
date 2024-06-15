package com.obed.retoCP2024;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@OpenAPIDefinition
@EnableJpaRepositories
public class RetoCp2024Application {

	public static void main(String[] args) {
		SpringApplication.run(RetoCp2024Application.class, args);
	}

}
