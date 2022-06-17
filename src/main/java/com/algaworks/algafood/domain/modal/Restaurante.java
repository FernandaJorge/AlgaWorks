package com.algaworks.algafood.domain.modal;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY) //Indica que muitos restaurantes possuem muitas cozinhas, com fecth eu indico para fazer a consulta do Tipo Lazy
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;
	
	@JsonIgnore
	@Embedded
	private Endereco endereco;
	
	@CreationTimestamp //anotacao do hibernate que implementa a data de criação automaticamente
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataCadastro;
	
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime") //anotacao do hibernate que implementa a data de atualizacao automaticamente
	private LocalDateTime dataAtualizacao;
	
	@JsonIgnore
	@ManyToMany //Incdica que muitos restaurantes possuem muitas formas de pagamento
	@JoinTable(name = "restaurante_forma_pagamento", //crio a tabela de relacionamento
			joinColumns = @JoinColumn(name = "restaurante_id"), //crio o nome da coluna (forenkey) da tab de relacionamento com a de restaurante
			inverseJoinColumns = @JoinColumn(name= "forma_pagamento_id")) //crio o nome da coluna (forenkey) da tab de relacionamento com a da forma de pag
	private List<FormaPagamento> formasPagamento = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();
	
}
