package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
													@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		return service.consultarVendasDiarias(filtro, timeOffset);
	}

}
