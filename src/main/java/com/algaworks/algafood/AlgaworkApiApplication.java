package com.algaworks.algafood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomJpaRepositoryImpl.class)
public class AlgaworkApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgaworkApiApplication.class, args);
	}

}
