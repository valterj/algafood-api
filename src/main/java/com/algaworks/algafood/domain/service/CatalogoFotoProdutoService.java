package com.algaworks.algafood.domain.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;

import lombok.AllArgsConstructor;

@Service
@Transactional
@Repository
@AllArgsConstructor
public class CatalogoFotoProdutoService {

	private ProdutoRepository produtoRepository;

	public FotoProduto salvar(FotoProduto foto) {
		return this.produtoRepository.save(foto);
	}

}
