package com.projeto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.projeto.dto.ProdutoDTO;
import com.projeto.service.ProdutoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/produtos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProdutoController {
      private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService){
        this.produtoService = produtoService;
    }
    @GetMapping
    public List<ProdutoDTO> lista() {
        return produtoService.lista();
    }

    @GetMapping("/{id}")
    public ProdutoDTO findById(@PathVariable @NotNull @Positive Long id){
        return produtoService.findById(id);

    }
    //medoto
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProdutoDTO adicionar(@RequestBody @Valid ProdutoDTO produto){
        System.out.println(produto);
        return produtoService.adicionar(produto);
    }
    @PutMapping("/{id}")
    public ProdutoDTO editar(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid ProdutoDTO produto){
        return produtoService.editar(id, produto);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable @NotNull @Positive Long id){
        produtoService.excluir(id);
    }
    
}
