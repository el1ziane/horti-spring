package com.projeto.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.projeto.dto.EstoqueDTO;
import com.projeto.model.Produto;
import com.projeto.service.EstoqueService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/estoque")
@CrossOrigin(origins = "http://localhost:4200")
public class EstoqueController {
      private final EstoqueService estoqueService;

    public EstoqueController(EstoqueService estoqueService){
        this.estoqueService = estoqueService;
    }

    @GetMapping
    public List<EstoqueDTO> lista() {
        return estoqueService.lista();
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/produto/{produtoNome}")
    public Produto findByProdutoNome(@PathVariable @NotNull @NotBlank String produtoNome) {
        return estoqueService.findByProdutoNome(produtoNome);
    }
    

    @GetMapping("/{id}")
    public EstoqueDTO findById(@PathVariable @NotNull @Positive Long id){
        return estoqueService.findById(id);
    }
    @GetMapping("/quantidade") 
    public ResponseEntity<Integer> getQuantidadeEstoque() {
        int quantidade = estoqueService.getQuantidadeEstoque();
        return ResponseEntity.ok(quantidade);
    }

    @PutMapping("/{id}/remover")
    public ResponseEntity<EstoqueDTO> removerQuantidadeEstoque(
        @PathVariable Long id,
        @RequestBody int quantidade) {
        EstoqueDTO atualizado = estoqueService.removerQuantidadeEstoque(id, quantidade);
        return ResponseEntity.ok(atualizado);
    }
    


}


