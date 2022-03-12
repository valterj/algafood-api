package com.algaworks.algafood.domain.exception;

public class NegocioException extends RuntimeException {

	private static final long serialVersionUID = 5107537605883591722L;

	public NegocioException() {
		super();
	}

	public NegocioException(String message) {
		super(message);
	}

	public NegocioException(String message, Throwable cause) {
		super(message, cause);
	}

}
