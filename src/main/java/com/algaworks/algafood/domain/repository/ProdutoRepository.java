package com.algaworks.algafood.domain.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algaworks.algafood.domain.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	public Collection<Produto> findByRestauranteId(Long restauranteId);

	public Optional<Produto> findByIdAndRestauranteId(Long id, Long restauranteId);

}
