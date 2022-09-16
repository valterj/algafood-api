package com.algaworks.algafood.domain.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;

public interface ProdutoRepository extends JpaRepository<Produto, Long>, ProdutoRepositoryQueries {

	public Collection<Produto> findByRestauranteId(Long restauranteId);

	public Optional<Produto> findByIdAndRestauranteId(Long id, Long restauranteId);

	@Query("FROM Produto p WHERE p.ativo = true AND p.restaurante = :restaurante")
	List<Produto> findAtivosByRestaurante(Restaurante restaurante);

}
