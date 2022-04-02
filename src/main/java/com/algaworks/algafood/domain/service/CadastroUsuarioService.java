package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.SenhaInvalidaException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;

@Service
@Transactional
public class CadastroUsuarioService {

	@Autowired
	private UsuarioRepository repository;

	public Usuario buscar(Long id) {
		return repository.findById(id).orElseThrow(() -> new UsuarioNaoEncontradoException(id));
	}

	public Usuario salvar(Usuario usuario) {
		return repository.save(usuario);
	}

	public void excluir(Long id) {
		repository.deleteById(id);
	}

	public void alterarSenha(Long id, String senhaAtual, String novaSenha) {

		var usuario = buscar(id);

		if (usuario.senhaCoincideCom(senhaAtual)) {
			usuario.setSenha(novaSenha);
		} else {
			throw new SenhaInvalidaException();
		}

	}

}
