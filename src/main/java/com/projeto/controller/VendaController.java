package com.projeto.controller;

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

import com.projeto.dto.VendaDTO;
import com.projeto.model.Venda;
import com.projeto.service.VendaService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/vendas")
@CrossOrigin(origins = "http://localhost:4200")
public class VendaController {
    
    private final VendaService vendaService;

    public VendaController(VendaService vendaService){
        this.vendaService = vendaService;
    }
    @GetMapping
    public List<VendaDTO> lista() {
        return vendaService.lista();
    }

    @GetMapping("/{id}")
    public VendaDTO findById(@PathVariable @NotNull @Positive Long id){
        return vendaService.findById(id);

    }
    //medoto
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Venda adicionar(@RequestBody @Valid VendaDTO venda){
        System.out.println(venda);
        return vendaService.adicionar(venda);
    }
    @PutMapping("/{id}")
    public VendaDTO editar(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid VendaDTO venda){
        return vendaService.editar(id, venda);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable @NotNull @Positive Long id){
        vendaService.excluir(id);
    }
}
