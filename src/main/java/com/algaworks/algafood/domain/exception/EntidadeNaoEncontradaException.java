package com.algaworks.algafood.domain.exception;

public abstract class EntidadeNaoEncontradaException extends NegocioException {

	private static final long serialVersionUID = 1L;

	protected EntidadeNaoEncontradaException() {
		super("Entidade não encontrada");
	}

	protected EntidadeNaoEncontradaException(String message) {
		super(message);
	}

}
