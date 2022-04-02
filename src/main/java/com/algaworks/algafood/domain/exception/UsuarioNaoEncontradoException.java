package com.algaworks.algafood.domain.exception;

public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = -6184801988663844978L;

	private static final String MSG_USUARIO_NAO_ENCONTRADO = "Não existe um cadastro de usuário com código %d";

	public UsuarioNaoEncontradoException() {
		this("Não existe usuário cadastrado");
	}

	public UsuarioNaoEncontradoException(Long id) {
		super(String.format(MSG_USUARIO_NAO_ENCONTRADO, id));
	}

	public UsuarioNaoEncontradoException(String message) {
		super(message);
	}

}
