package com.algaworks.algafood.api.controller;

import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.modal.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService restauranteService;
	
	@GetMapping
	public List<Restaurante> listar() {
		return restauranteRepository.findAll();
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscaPorId(@PathVariable Long restauranteId){
		Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
		
		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping()
	//Usando o ? no ResponseEntity é um Coringa, neste caso ele pode receber como retorno 
	//uma String ou uma instancia de restaurante
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) { 
		try {
			restaurante = restauranteService.salvar(restaurante);	
			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
		try {
			Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
			
			if (restauranteAtual.isPresent()) {
				BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id");
				
				Restaurante restauranteSalvo = restauranteService.salvar(restauranteAtual.get());
				return ResponseEntity.ok(restauranteSalvo);
			}
			
			return ResponseEntity.notFound().build();
		
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	//No PatchMapping não é apropriado usar o Objeto(Restaurante) para atualizar os campos porque não
	//é recomendado que verifiquemos item pode item do objeto para saber qual esta nulo ou não para salvar,
	//mesmo pq podemos querer atualizar algo para nulo, para isso é o usado o Map
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos){
		
		Restaurante restauranteAtual = restauranteRepository.findById(restauranteId).orElse(null);
		
		if (restauranteAtual == null) {
			return ResponseEntity.notFound().build();
		}
		
		merge(campos, restauranteAtual);
		
		return atualizar(restauranteId, restauranteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		
		//alguns campos não são atualizados por serem de tipos diferentes achadas pelo reflectionutils, para resolver
		//isso vamos utilizar o ObjectMapper que converte(serializa) um campo jason em objeto do tipo java
		ObjectMapper objectMapper = new ObjectMapper();
		
		//instancio o restaurante passando o campo com o novo valor
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
		
		System.out.println(restauranteOrigem);
		
		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			
			// para atribuir os campos vamos usar o Reflections do Spring = capacidade de inspecionar objetos java em tempo
			// de execução e alterar esses obejetos de forma dinamica, especialmente qdo não sabemos o nomes dos metodos, propriedades etc
			
			
			//busco uma popriedade dentro de uma classe, com o atributo passado e implemento isso na variavel field
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			
			//dou acesso as variaves privadas
			field.setAccessible(true);
					
			//para resolver o caso do tipo diferente de campos vamos dar um get dinamico no restauranteOrigem
			//passando ele para o restauranteDestino e não mais o valorPropriedade
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
			//System.out.println(nomePropriedade +"="+ valorPropriedade +"="+ novoValor);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);;
		});
	}
	
	@GetMapping("/portaxafrete")
	public List<Restaurante> restaurantePorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.queryByTaxaFreteBetween(taxaInicial, taxaFinal);
	}
	
	@GetMapping("/pornome-cozinha")
	public List<Restaurante> restaurantePorNomeCozinha(String nome, Long cozinha) {
		return restauranteRepository.findByNomeContainingAndCozinhaId(nome, cozinha);
	}
	
	@GetMapping("/primeiro-pornome")
	public Optional<Restaurante> restaurantePrimeiroPorNome(String nome) {
		return restauranteRepository.findFirstByNomeContaining(nome);
	}
	
	@GetMapping("/top2-pornome")
	public List<Restaurante> restauranteTop2PorNome(String nome) {
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}
	
	@GetMapping("/count-porcozinha")
	public int restaurantesPorCozinha(Long id) {
		return restauranteRepository.countByCozinhaId(id);
	}
	
	@GetMapping("/pornome")
	public List<Restaurante> restaurantePorNome(String nome) {
		return restauranteRepository.consultaPorNome(nome);
	}
	
	@GetMapping("/pornome-frete")
	public List<Restaurante> restaurantePorNomeeTaxa(String nome, BigDecimal taxaInicial, BigDecimal taxaFinal) {
		return restauranteRepository.find(nome, taxaInicial, taxaFinal);
	}
	
	//Usando Specification
	@GetMapping("/com-frete-gratis")
	public List<Restaurante> restauranteComFreteGratis(String nome) {
		
		return restauranteRepository.findComFreteGratis(nome);
	}
	
	//busca pelo repositorio customizado
	@GetMapping("/primeiro")
	public Optional<Restaurante> restaurantePrimeiro() {	
		return restauranteRepository.buscarPrimeiro();
	}
}


