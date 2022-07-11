package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.core.modelmapper.GenericModelAssembler;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.service.CadastroGrupoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
@AllArgsConstructor
public class GrupoPermissaoController {

	private CadastroGrupoService cadastroGrupo;

	private GenericModelAssembler<Permissao, PermissaoModel> assembler;

	@GetMapping
	public List<PermissaoModel> listar(@PathVariable Long grupoId) {
		var grupo = cadastroGrupo.buscar(grupoId);
		return assembler.toCollectionModel(grupo.getPermissoes(), PermissaoModel.class);
	}

	@PutMapping("/{permissaoId}")
	public void adicionarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupo.adicionarPermissao(grupoId, permissaoId);
	}

	@DeleteMapping("/{permissaoId}")
	public void removerPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		cadastroGrupo.removerPermissao(grupoId, permissaoId);
	}

}
