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
import com.projeto.dto.FornecedorDTO;
import com.projeto.model.Fornecedor;
import com.projeto.service.FornecedorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/fornecedores")
@CrossOrigin(origins = "http://localhost:4200")
public class FornecedorController {

    private final FornecedorService fornecedorService;

    public FornecedorController(FornecedorService fornecedorService) {
        this.fornecedorService = fornecedorService;
    }

    @GetMapping
    public List<FornecedorDTO> lista() {
        return fornecedorService.lista();
    }

    @GetMapping("/{id}")
    public FornecedorDTO findById(@PathVariable @NotNull @Positive Long id) {
        return fornecedorService.findById(id);

    }

    // medoto
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Fornecedor adicionar(@RequestBody @Valid FornecedorDTO fornecedor) {
        System.out.println(fornecedor);
        return fornecedorService.adicionar(fornecedor);
    }

    @PutMapping("/{id}")
    public FornecedorDTO editar(@PathVariable @NotNull @Positive Long id,
            @RequestBody @Valid FornecedorDTO fornecedor) {
        return fornecedorService.editar(id, fornecedor);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable @NotNull @Positive Long id) {
        fornecedorService.excluir(id);
    }
}