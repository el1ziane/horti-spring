package com.projeto.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.dto.EstoqueDTO;
import com.projeto.service.EstoqueService;

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

    @GetMapping("/{id}")
    public EstoqueDTO findById(@PathVariable @NotNull @Positive Long id){
        return estoqueService.findById(id);
    }

}
