package com.algaworks.algafood.api.model;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioModel {

	@NotBlank
	private String nome;

	@NotBlank
	private String email;

}
