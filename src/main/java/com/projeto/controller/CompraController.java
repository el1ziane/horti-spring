package com.projeto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.projeto.dto.CompraDTO;
import com.projeto.service.CompraService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/compras")
@CrossOrigin(origins = "http://localhost:4200")
public class CompraController {
    
    private final CompraService compraService;

    public CompraController(CompraService compraService){
        this.compraService = compraService;
    }
    @GetMapping
    public List<CompraDTO> lista() {
        return compraService.lista();
    }

    @GetMapping("/{id}")
    public CompraDTO findById(@PathVariable @NotNull @Positive Long id){
        return compraService.findById(id);
    }
    //medoto
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CompraDTO adicionar(@RequestBody @Valid CompraDTO compra){
        System.out.println(compra);
        return compraService.adicionar(compra);
    }
    @PutMapping("/{id}")
    public CompraDTO editar(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid CompraDTO compra){
        return compraService.editar(id, compra);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable @NotNull @Positive Long id){
        compraService.excluir(id);
    }

    @GetMapping("/total-compras-pendentes")
    public ResponseEntity<Double> getTotalComprasPendentes() {
        double totalCompra = compraService.getTotalComprasPendentes();
        return ResponseEntity.ok(totalCompra);
    }
}
