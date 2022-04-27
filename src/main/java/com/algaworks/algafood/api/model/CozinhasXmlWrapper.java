package com.algaworks.algafood.api.model;

import java.util.List;

import com.algaworks.algafood.domain.modal.Cozinha;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import lombok.Data;
import lombok.NonNull;

@JacksonXmlRootElement(localName = "cozinhas")
@Data
public class CozinhasXmlWrapper {
	
	@JacksonXmlProperty(localName = "cozinha")
	//embrulho de um elemento de xml usando a propriedade userWrapping estou desabilitando um embrulho
	@JacksonXmlElementWrapper(useWrapping = false)   
	@NonNull
	private List<Cozinha> cozinhas;

}
