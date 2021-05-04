package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.repository.CozinhaRepository;

public class ConsultaCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository bean = applicationContext.getBean(CozinhaRepository.class);
				
		/*Cozinha cozinha1 = new Cozinha();
		cozinha1.setNome("Brasileira");
		bean.adicionar(cozinha1);
		
		Cozinha cozinha2 = new Cozinha();
		cozinha2.setNome("Japonesa");
		bean.adicionar(cozinha2);*/
		
		Cozinha cozinha = new Cozinha();
		cozinha.setId(1L);
		/*cozinha.setNome("Brasileira teste alteração");*/
		bean.remover(cozinha);
		
		List<Cozinha> cozinhas = bean.listar();
		cozinhas.forEach(System.out::println);
		
		/*Cozinha cozinha3 = bean.buscar(1L);
		System.out.println(cozinha3);*/
	}

}
