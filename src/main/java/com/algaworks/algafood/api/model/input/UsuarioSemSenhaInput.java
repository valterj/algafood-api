package com.algaworks.algafood.api.model.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;

@Getter
public class UsuarioSemSenhaInput {

	@NotBlank
	private String nome;

	@NotBlank
	@Email
	private String email;

}
