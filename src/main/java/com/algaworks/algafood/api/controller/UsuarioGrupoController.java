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

import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.core.modelmapper.GenericModelAssembler;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

	private CadastroUsuarioService cadastroUsuarioService;

	private GenericModelAssembler<Grupo, GrupoModel> assembler;

	@GetMapping
	public List<GrupoModel> listar(@PathVariable Long usuarioId) {
		var usuario = cadastroUsuarioService.buscar(usuarioId);
		return assembler.toCollectionModel(usuario.getGrupos(), GrupoModel.class);
	}

	@DeleteMapping("/{grupoId}")
	@ResponseStatus(NO_CONTENT)
	public void desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuarioService.desassociarGrupo(usuarioId, grupoId);
	}

	@PutMapping("/{grupoId}")
	@ResponseStatus(NO_CONTENT)
	public void associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
		cadastroUsuarioService.associarGrupo(usuarioId, grupoId);
	}

}
