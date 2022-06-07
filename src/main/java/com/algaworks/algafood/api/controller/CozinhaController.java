package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.modal.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {
	
	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Cozinha> listar() {
		return cozinhaRepository.findAll();
	}
	
	
	@GetMapping("/{cozinhaId}")
	//ReponseEntity permite ter uma resposta mais fina do Http como adicionar Headers, 
	//mudar o status http de uma forma mais programatica, representa uma respota http que pode 
	//até uma instancia dentro da reposta
	public ResponseEntity<Cozinha> buscar(@PathVariable Long cozinhaId) {
		Optional<Cozinha> cozinha = cozinhaRepository.findById(cozinhaId);
		
		//uma forma de usar o RESPONSEENTITY é com o FUND que é movendo o link de resposta, para isso 
		//configuramos o header de resposta passando o novo link de direcionamento
		//dentro HTTPHEADERS como em HTTPSTATUS, temos todos os tipos de headers que podemos utilizar		
		//HttpHeaders header = new HttpHeaders();
		//header.add(HttpHeaders.LOCATION, "http://localhost:8080/cozinhas");
		//return ResponseEntity.status(HttpStatus.FOUND).headers(header).build();
		
		//return ResponseEntity.status(HttpStatus.OK).build() //desta forma eu retorno a resposta http sem o corpo

		//return ResponseEntity.status(HttpStatus.OK).body(cozinha); //Retorno a resposta http com o corpo da resposta
		//return ResponseEntity.ok(cozinha); // resposta simples e curta que representa a mesma coisa de cima
		
		//retornando quando uma resource é inexistente
		//verifico se esta presente
		if (cozinha.isPresent()) {
			return ResponseEntity.ok(cozinha.get());
		}
		
		//caso não exista retorna um 404
		//return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); //forma longa
		return ResponseEntity.notFound().build(); //forma curta
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Cozinha adicionar(@RequestBody Cozinha cozinha) { //O @RequestBody indica o parameto será vinculado com o corpo da requisicao 
		return cadastroCozinhaService.salvar(cozinha);
	}

	@PutMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
		 Optional<Cozinha> cozinhaAtual = cozinhaRepository.findById(cozinhaId);
		
		if (cozinhaAtual.isPresent()) {
			//cozinhaAtual.setNome(cozinha.getNome()); // pode ser feito assim obj por obj
		
			//Quando queremos atualizar todos os objetos de uma vez uso
			//O BeanUtils.copyProperties indica que estou copiando os parametros passados para o corpo para instacia atual
			//o terceiro parametro eu indico que estou excluindo o id da atualização pq não modifico ele
			BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id"); 
		
			Cozinha cozinhaSalva = cadastroCozinhaService.salvar(cozinhaAtual.get());
			
			return ResponseEntity.ok(cozinhaSalva);
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	@DeleteMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long cozinhaId) {
		try {
			cadastroCozinhaService.excluir(cozinhaId);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
			
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}
	
	
	@GetMapping("/pornome")
	public List<Cozinha> consultaPorNome(String nome){
		return cozinhaRepository.findTodasByNomeContaining(nome);
	}
	
	@GetMapping("/unica-pornome")
	public Optional<Cozinha> unicaPorNome(String nome){
		return cozinhaRepository.findByNome(nome);
	}
	
	@GetMapping("/exists")
	public boolean cozinhaExists(String nome){
		return cozinhaRepository.existsByNome(nome);
	} 
}


