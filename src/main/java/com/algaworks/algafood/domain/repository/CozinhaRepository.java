package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.modal.Cozinha;

public interface CozinhaRepository {
	
	List<Cozinha> listaTodas();
	Cozinha porId(Long id);
	Cozinha salvar (Cozinha cozinha);
	void remover(Cozinha cozinha);
}
