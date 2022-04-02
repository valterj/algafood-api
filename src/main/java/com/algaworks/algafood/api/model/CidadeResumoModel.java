package com.algaworks.algafood.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CidadeResumoModel {

	private Long id;
	private String nome;

	@JsonProperty("estado")
	private String nomeEstado;

}
