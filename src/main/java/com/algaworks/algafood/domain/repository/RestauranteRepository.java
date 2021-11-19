package com.algaworks.algafood.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepository 
    extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, 
    JpaSpecificationExecutor<Restaurante> {
    
	/**
	 * Resolve o problema do N+1
	 */
    @Query("from Restaurante r join r.cozinha")
    List<Restaurante> findAll();

}
