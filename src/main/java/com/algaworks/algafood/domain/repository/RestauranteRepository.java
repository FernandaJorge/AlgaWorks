package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.modal.Restaurante;

public interface RestauranteRepository {
	
	List<Restaurante> listaTodos();
	Restaurante porId(Long id);
	Restaurante adicionar (Restaurante restaurante);
	void remover(Restaurante restaurante);
}
