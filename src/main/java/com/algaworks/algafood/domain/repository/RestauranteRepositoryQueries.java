package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.algaworks.algafood.domain.modal.Restaurante;

public interface RestauranteRepositoryQueries {

	List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

}