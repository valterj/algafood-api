package com.algaworks.algafood.domain.exception;

public class SenhaInvalidaException extends NegocioException {

	private static final long serialVersionUID = -487967734348903753L;

	private static final String SENHA_INVALIDA = "Senha atual informada não coincide com a senha do usuário";

	public SenhaInvalidaException() {
		super(SENHA_INVALIDA);
	}

}
