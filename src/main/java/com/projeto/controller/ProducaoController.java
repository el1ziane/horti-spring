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

import com.projeto.dto.ProducaoDTO;

import com.projeto.service.ProducaoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/producao")
@CrossOrigin(origins = "http://localhost:4200")
public class ProducaoController {
       private final ProducaoService producaoService;

    public ProducaoController(ProducaoService producaoService){
        this.producaoService = producaoService;
    }
    @GetMapping
    public List<ProducaoDTO> lista() {
        return producaoService.lista();
    }

    @GetMapping("/{id}")
    public ProducaoDTO findById(@PathVariable @NotNull @Positive Long id){
        return producaoService.findById(id);

    }
    //medoto
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProducaoDTO adicionar(@RequestBody @Valid ProducaoDTO producao){
        System.out.println(producao);
        return producaoService.adicionar(producao);
    }
    @PutMapping("/{id}")
    public ProducaoDTO editar(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid ProducaoDTO producao){
        return producaoService.editar(id, producao);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable @NotNull @Positive Long id){
        producaoService.excluir(id);
    }
}
