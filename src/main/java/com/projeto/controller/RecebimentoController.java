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

import com.projeto.dto.RecebimentoDTO;
import com.projeto.model.Produto;
import com.projeto.service.RecebimentoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/recebimentos")
@CrossOrigin(origins = "http://localhost:4200")
public class RecebimentoController {
    
    private final RecebimentoService recebimentoService;

    public RecebimentoController(RecebimentoService recebimentoService){
        this.recebimentoService = recebimentoService;
    }
    @GetMapping
    public List<RecebimentoDTO> lista() {
        return recebimentoService.lista();
    }

    @GetMapping("/{id}")
    public RecebimentoDTO findById(@PathVariable @NotNull @Positive Long id){
        return recebimentoService.findById(id);

    }
    

    //medoto
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public RecebimentoDTO adicionar(@RequestBody @Valid RecebimentoDTO recebimento){
        System.out.println(recebimento);
        return recebimentoService.adicionar(recebimento);
    }
    @PutMapping("/{id}")
    public RecebimentoDTO editar(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid RecebimentoDTO recebimento){
        return recebimentoService.editar(id, recebimento);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable @NotNull @Positive Long id){
        recebimentoService.excluir(id);
    }

    @GetMapping("/total-compras-pendentes")
    public ResponseEntity<Double> getTotalComprasPendentes() {
        double totalRecebimento = recebimentoService.getTotalComprasPendentes();
        return ResponseEntity.ok(totalRecebimento);
    }
}
