package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private CadastroCidadeService cadastroCidade;
	
	@GetMapping
	public List<Cidade> listar() {
		return this.cidadeRepository.listar();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long id) {
	    Cidade cidade = this.cidadeRepository.buscar(id);
	    
	    if (cidade != null) {
	       return ResponseEntity.ok(cidade);
	    }
	    
	    return ResponseEntity.notFound().build();
	}
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade salvar(@RequestBody Cidade cidade) {
	    return this.cadastroCidade.salvar(cidade);	    
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Cidade cidade) {
	    Cidade cidadeAtual = this.cidadeRepository.buscar(id);
	    
	    if (cidadeAtual != null) {
            BeanUtils.copyProperties(cidade, cidadeAtual, "id");
            
            try {
                cidadeAtual = this.cadastroCidade.salvar(cidadeAtual);
                return ResponseEntity.ok(cidadeAtual);
            } catch (EntidadeNaoEncontradaException e) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
            }
        }

        return ResponseEntity.notFound().build();	    
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> remover(@PathVariable Long id) {
	    try {
	        this.cadastroCidade.excluir(id);
	        return ResponseEntity.noContent().build();
	    } catch (EntidadeNaoEncontradaException e) {
	        return ResponseEntity.notFound().build();
	    }
	}

}
