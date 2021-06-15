package com.algaworks.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.algaworks.algafood.domain.model.Restaurante;

public interface RestauranteRepository 
    extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, 
    JpaSpecificationExecutor<Restaurante> {

}
