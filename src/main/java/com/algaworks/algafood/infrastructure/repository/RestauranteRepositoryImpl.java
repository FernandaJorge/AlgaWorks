package com.algaworks.algafood.infrastructure.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.algaworks.algafood.domain.modal.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;


@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		
		
		//o CriteriaBuilder é uma biblioteca que tem vários atributos que podemos usar em uma consulta avançada
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		
		// Aqui eu instancio meu criteria passando a classe que eu vou realizar a consulta
		CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
		Root<Restaurante> root = criteria.from(Restaurante.class);//from Restaurante retorna um Root - representa a raiz então o Restaurante
		
		//criando um array list dos predicados para uma consulta dinamica
		
		var predicates = new ArrayList<Predicate>();
		
		if (StringUtils.hasText(nome)) {
			predicates.add(builder.like(root.get("nome"), "%" + nome + "%" ));
		}
		
		if (taxaFreteInicial != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
		}

		if (taxaFreteFinal != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
		}

		
		// o list de array em array passando um array 0
		criteria.where(predicates.toArray(new Predicate[0]));
		
		TypedQuery<Restaurante> query = manager.createQuery(criteria);
		return query.getResultList();
	}

}
