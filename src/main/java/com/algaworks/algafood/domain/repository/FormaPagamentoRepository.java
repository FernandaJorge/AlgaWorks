package com.algaworks.algafood.domain.repository;

import java.util.List;

import com.algaworks.algafood.domain.modal.FormaPagamento;

public interface FormaPagamentoRepository {
	
	List<FormaPagamento> listaTodos();
	FormaPagamento porId(Long id);
	FormaPagamento adicionar (FormaPagamento formaPagamento);
	void remover(FormaPagamento formaPagamento);
}
