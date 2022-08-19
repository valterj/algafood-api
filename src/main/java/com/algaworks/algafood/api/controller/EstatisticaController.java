package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.VendaQueryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/estatisticas")
@AllArgsConstructor
public class EstatisticaController {

	private VendaQueryService service;

	@GetMapping("/vendas-diarias")
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro) {
		return service.consultarVendasDiarias(filtro);
	}

}
