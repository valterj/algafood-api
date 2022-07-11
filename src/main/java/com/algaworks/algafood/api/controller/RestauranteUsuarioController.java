package com.algaworks.algafood.api.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.core.modelmapper.GenericModelAssembler;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/usuarios")
@AllArgsConstructor
public class RestauranteUsuarioController {

	private CadastroRestauranteService cadastroRestauranteService;

	private GenericModelAssembler<Usuario, UsuarioModel> assembler;

	@GetMapping
	public List<UsuarioModel> listar(@PathVariable Long restauranteId) {
		var restaurante = cadastroRestauranteService.buscar(restauranteId);
		return assembler.toCollectionModel(restaurante.getUsuarios(), UsuarioModel.class);
	}

	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(NO_CONTENT)
	public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestauranteService.desassociarResponsavel(restauranteId, usuarioId);
	}

	@PutMapping("/{usuarioId}")
	@ResponseStatus(NO_CONTENT)
	public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		cadastroRestauranteService.associarResponsavel(restauranteId, usuarioId);
	}

}
