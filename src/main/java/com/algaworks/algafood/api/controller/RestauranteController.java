package com.algaworks.algafood.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    
    @Autowired
    private RestauranteRepository restauranteRepository;
    
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;
    
    @GetMapping
    public List<Restaurante> listar() {
        return this.restauranteRepository.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
        Optional<Restaurante> restaurante = this.restauranteRepository.findById(id);
        
        if (restaurante.isPresent()) {
            return ResponseEntity.ok(restaurante.get());
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = this.cadastroRestaurante.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
   
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        Optional<Restaurante> restauranteAtual = this.restauranteRepository.findById(id);
        
        if (restauranteAtual.isPresent()) {
            BeanUtils.copyProperties(restaurante, restauranteAtual.get(), 
                    "id", "formasPagamento", "endereco", "dataCadastro");
            var restauranteSalvo = this.cadastroRestaurante.salvar(restauranteAtual.get());
            return ResponseEntity.ok(restauranteSalvo);
        }
        
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/com-frete-gratis")
    public List<Restaurante> restaurantesComFreteGratis(String nome) {
        return this.restauranteRepository.findComFreteGratis(nome);
    }
    
    @GetMapping("/primeiro")
    public Optional<Restaurante> findFirst(String nome) {
        return this.restauranteRepository.buscarPrimeiro();
    }

}
