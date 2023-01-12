package com.algaworks.algafood.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FotoProduto {

	@Id
	@EqualsAndHashCode.Include
	@Column(name = "produtoId")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@MapsId
	private Produto produto;

	private String nomeArquivo;

	private String descricao;

	private String contentType;

	private Long tamanho;

	public Long getRestauranteId() {

		if (this.produto != null) {
			return this.produto.getRestaurante().getId();
		}

		return null;
	}

}
