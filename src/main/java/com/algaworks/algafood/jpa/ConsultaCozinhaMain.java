package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgaworkApiApplication;
import com.algaworks.algafood.domain.modal.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

public class ConsultaCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgaworkApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cadastroRepository = applicationContext.getBean(CozinhaRepository.class);
		
		List<Cozinha> todasCozinhas =  cadastroRepository.listaTodas();
		
		for (Cozinha cozinha: todasCozinhas) {
			System.out.println(cozinha.getNome());
		}
	}
}
