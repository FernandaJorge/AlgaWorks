package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.modal.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {
	
	// para implementar novos metodo usando nome de propriedades ex:nome
	// sempre podemos criar um metodo novo usando o findBy como prefixo
	//O JPA tem KeyWord para definirmos tipos de pesquisa como o Containing que funciona como Like
	List<Cozinha> findTodasByNomeContaining(String nome);
	
	Optional<Cozinha> findByNome(String nome);
	
	//Outra forma de pesquisa com palavra chave é o Exists que reton um boolean
	boolean existsByNome(String nome);
}
