package com.algaworks.algafood.domain.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.algaworks.algafood.domain.modal.Restaurante;

public interface RestauranteRepository 
	extends JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries,
		JpaSpecificationExecutor<Restaurante>{
	
	//Existem outros prefixos que podemos usar além do findBy, como por exemplo: readBy, getBy, queryBy ou streamBy
	List<Restaurante> queryByTaxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);
	
	List<Restaurante> findByNomeContainingAndCozinhaId(String nome, Long cozinha);
	
	//First é uma outra paralavra chave para trazer o primeiro da lista como resultado
	Optional<Restaurante> findFirstByNomeContaining(String nome);
	
	
	//Top1 é uma outra paralavra chave para trazer os 2 primeirosda lista como resultado
	List<Restaurante> findTop2ByNomeContaining(String nome);
	
	//Count palavra chave para trazer a quantidade
	int countByCozinhaId(Long cozinha);

	//Quando queremos usar nomes nossos para um metodo devemos utilizar a anotação @Query 
	//passando como propriedade uma consulta query 
	//@Query("from Restaurante where nome like %:nome%")
	// -podemos organizar melhor os JPQL externalizando as consultas em um arquivo XML, criamos
	//ele com o nome de ORM.XML
	List<Restaurante> consultaPorNome(String nome);
	
}
