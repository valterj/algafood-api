package com.algaworks.algafood.infrastructure.service;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filter, String timeOffset) {

		var builder = manager.getCriteriaBuilder();
		var query = builder.createQuery(VendaDiaria.class);
		var root = query.from(Pedido.class);

		var functionConvertTzDataCriacao = builder.function("convert_tz",
															Date.class,
															root.get("dataCriacao"),
															builder.literal("+00:00"),
															builder.literal(timeOffset));

		var functionDateDataCriacao = builder.function("date", Date.class, functionConvertTzDataCriacao);

		var selection = builder.construct(VendaDiaria.class,
										  functionDateDataCriacao,
										  builder.count(root.get("id")),
										  builder.sum(root.get("valorTotal")));

		query.select(selection);
		query.groupBy(functionDateDataCriacao);

		return manager.createQuery(query).getResultList();
	}

}
