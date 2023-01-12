package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import com.algaworks.algafood.infrastructure.repository.spec.RestauranteSpecs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	@Lazy
	private RestauranteRepository restauranteRepository;

	@Override
	public List<Restaurante> findComFreteGratis(String nome) {
		return this.restauranteRepository.findAll(RestauranteSpecs.comFreteGratis()
																  .and(RestauranteSpecs.comNomeSemelhante(nome)));
	}

}
