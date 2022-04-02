package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;

@Service
@Transactional
public class CadastroGrupoService {

	@Autowired
	private GrupoRepository repository;

	public Grupo buscar(Long id) {
		return repository.findById(id).orElseThrow(() -> new GrupoNaoEncontradoException(id));
	}

	public Grupo salvar(Grupo grupo) {
		return repository.save(grupo);
	}

	public void remover(Long id) {
		try {
			repository.deleteById(id);
			repository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new GrupoNaoEncontradoException(id);
		}
	}

}
