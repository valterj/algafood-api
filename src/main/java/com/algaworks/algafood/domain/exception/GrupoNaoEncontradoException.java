package com.algaworks.algafood.domain.exception;

public class GrupoNaoEncontradoException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = -4203573350682788964L;

	private static final String MSG_GRUPO_NAO_ENCONTRADO = "Não existe um cadastro de grupo com código %d";

	public GrupoNaoEncontradoException(Long id) {
		this(String.format(MSG_GRUPO_NAO_ENCONTRADO, id));
	}

	private GrupoNaoEncontradoException(String message) {
		super(message);
	}

}
