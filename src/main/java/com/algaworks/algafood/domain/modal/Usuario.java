package com.algaworks.algafood.domain.modal;

public class Usuario {
	private String nome;
	private int idade;

	public Usuario(String nomeUsuario, int serial) {
	    super();
	    this.nome = nomeUsuario;
	    this.idade = serial;
	}

	@Override
	public String toString() {
	    return nome + " - " + idade;
	}

	public String getNome() {
	    return this.nome;
	}

	public int getIdade() {
	    return this.idade;
	}

	public void setNome(String nome) {
	    this.nome = nome;
	}

	public void setIdade(int idade) {
	    this.idade = idade;
	}
}
