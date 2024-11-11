package com.projeto.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.projeto.dto.DespesaDTO;
import com.projeto.model.Despesa;
import com.projeto.service.DespesaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/api/despesa")
@CrossOrigin(origins = "http://localhost:4200")
public class DespesaController {
     
    private final DespesaService despesaService;

    public DespesaController(DespesaService despesaService){
        this.despesaService = despesaService;
    }
    @GetMapping
    public List<DespesaDTO> lista() {
        return despesaService.lista();
    }

    @GetMapping("/{id}")
    public DespesaDTO findById(@PathVariable @NotNull @Positive Long id){
        return despesaService.findById(id);
    }
   
    //medoto
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Despesa adicionar(@RequestBody @Valid DespesaDTO despesa){
        System.out.println(despesa);
        return despesaService.adicionar(despesa);
    }
    @PutMapping("/{id}")
    public DespesaDTO editar(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid DespesaDTO despesa){
        return despesaService.editar(id, despesa);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable @NotNull @Positive Long id){
        despesaService.excluir(id);
    }
    @GetMapping("/total-despesas-pendentes")
    public ResponseEntity<Double> getTotalDespesaPendentes() {
        double totalDespesa = despesaService.getTotalDespesaPendentes();
        return ResponseEntity.ok(totalDespesa);
    }
}
