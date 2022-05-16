package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.modal.Estado;

public interface EstadoRepository {
	
	List<Estado> listaTodos();
	Estado porId(Long id);
	Estado adicionar (Estado estado);
	void remover(Long id);
}
