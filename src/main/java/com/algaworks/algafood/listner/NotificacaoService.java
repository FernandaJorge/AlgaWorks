package com.algaworks.algafood.listner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.notificacao.NivelUrgencia;
import com.algaworks.algafood.di.notificacao.Notificacao;
import com.algaworks.algafood.di.notificacao.TipoDeNotificacao;
import com.algaworks.algafood.di.service.ClienteAtivadoEvent;

@Component
public class NotificacaoService {
	
	@TipoDeNotificacao(NivelUrgencia.NORMAL)
	@Autowired()
	private Notificacao notificacao;
	
	@EventListener
	public void ClienteAtivadoListener(ClienteAtivadoEvent event) {
		notificacao.notificar(event.getCliente(), "Seu cadastro no sistema esta ativo!");
	}

}
