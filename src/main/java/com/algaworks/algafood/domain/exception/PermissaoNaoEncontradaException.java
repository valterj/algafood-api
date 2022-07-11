package com.algaworks.algafood.domain.exception;

public class PermissaoNaoEncontradaException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	private static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de permissão com código %d";

	public PermissaoNaoEncontradaException(Long id) {
		this(String.format(MSG_ESTADO_NAO_ENCONTRADO, id));
	}

	private PermissaoNaoEncontradaException(String message) {
		super(message);
	}

}
