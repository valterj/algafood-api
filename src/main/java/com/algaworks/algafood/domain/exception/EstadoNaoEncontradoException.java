package com.algaworks.algafood.domain.exception;

public class EstadoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;
    
    private static final String MSG_ESTADO_NAO_ENCONTRADO = "Não existe um cadastro de estado com código %d";
    
    public EstadoNaoEncontradoException(Long id) {
        this(String.format(MSG_ESTADO_NAO_ENCONTRADO, id));
    }
    
    private EstadoNaoEncontradoException(String message) {
        super(message);
    }

}
