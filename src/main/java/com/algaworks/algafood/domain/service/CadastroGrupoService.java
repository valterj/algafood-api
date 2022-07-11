package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.PermissaoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.repository.PermissaoRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@AllArgsConstructor
public class CadastroGrupoService {

	private GrupoRepository grupoRepository;

	private PermissaoRepository permissaoRepository;

	public Grupo buscar(Long id) {
		return grupoRepository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(id));
	}

	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}

	public void remover(Long id) {
		try {
			grupoRepository.deleteById(id);
			grupoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(id);
		}
	}

	public void adicionarPermissao(Long grupoId, Long permissaoId) {
		var grupo = buscar(grupoId);
		var permissao = permissaoRepository.findById(permissaoId)
										   .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
		grupo.adicionarPermissao(permissao);
	}

	public void removerPermissao(Long grupoId, Long permissaoId) {
		var grupo = buscar(grupoId);
		var permissao = permissaoRepository.findById(permissaoId)
										   .orElseThrow(() -> new PermissaoNaoEncontradaException(permissaoId));
		grupo.removerPermissao(permissao);
	}

}
