package com.algaworks.algafood.di.notificacao;

import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

@TipoDeNotificacao(NivelUrgencia.URGENTE)
@Component
public class NotificacaoSMS implements Notificacao {		

	@Override
	public void notificar(Cliente cliente, String mensagem) {	
		System.out.printf("Notificando %s por SMS atraves do telefone %s: %s\n", 
				cliente.getNome(), cliente.getTelefone(), mensagem);
	}
}
