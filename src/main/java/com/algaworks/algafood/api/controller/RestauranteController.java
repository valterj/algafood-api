package com.algaworks.algafood.api.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.assembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CadastroRestauranteService cadastroRestaurante;

	@Autowired
	private RestauranteModelAssembler assembler;

	@Autowired
	private RestauranteInputDisassembler disassembler;

	@GetMapping
	public List<RestauranteModel> listar() {
		var restaurantes = this.restauranteRepository.findAll();
		return assembler.toCollectionModel(restaurantes);
	}

	@GetMapping("/{id}")
	public RestauranteModel buscar(@PathVariable Long id) {
		return assembler.toModel(cadastroRestaurante.buscar(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
		try {
			var restaurante = cadastroRestaurante.salvar(this.disassembler.toDomainObject(restauranteInput));
			return assembler.toModel(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public RestauranteModel atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteInput restauranteInput) {

		Restaurante restauranteAtual = cadastroRestaurante.buscar(id);

		disassembler.copyToDomainObject(restauranteInput, restauranteAtual);

		try {
			return assembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage());
		}

	}

	@GetMapping("/com-frete-gratis")
	public List<RestauranteModel> restaurantesComFreteGratis(String nome) {
		var restaurantes = this.restauranteRepository.findComFreteGratis(nome);
		return assembler.toCollectionModel(restaurantes);
	}

	@GetMapping("/primeiro")
	public RestauranteModel findFirst(String nome) {
		return assembler.toModel(this.restauranteRepository.buscarPrimeiro()
														   .orElseThrow(RestauranteNaoEncontradoException::new));
	}

	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long id) {
		cadastroRestaurante.ativar(id);
	}

	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long id) {
		cadastroRestaurante.inativar(id);
	}

	@PutMapping("/{id}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long id) {
		cadastroRestaurante.abrir(id);
	}

	@PutMapping("/{id}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long id) {
		cadastroRestaurante.fechar(id);
	}

	@PutMapping("/ativacoes")
	@ResponseStatus(NO_CONTENT)
	public void ativarMultiplos(@RequestBody List<Long> restaurantesId) {
		try {
			cadastroRestaurante.ativar(restaurantesId);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/ativacoes")
	@ResponseStatus(NO_CONTENT)
	public void inativarMultiplos(@RequestBody List<Long> restaurantesId) {
		try {
			cadastroRestaurante.inativar(restaurantesId);
		} catch (RestauranteNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

}
