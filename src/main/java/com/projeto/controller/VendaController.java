package com.projeto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.dto.VendaDTO;
import com.projeto.model.Produto;
import com.projeto.service.VendaService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/vendas")

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

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/produto/{produtoNome}")
    public Produto findByProdutoNome(@PathVariable @NotNull @NotBlank String produtoNome) {
        return vendaService.findByProdutoNome(produtoNome);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public VendaDTO adicionar(@RequestBody @Valid VendaDTO venda){
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

    @GetMapping("/valor-total")
    public ResponseEntity<Double> getValorTotalVendas() {
        double valorTotal = vendaService.getValorTotalVendas();
        return ResponseEntity.ok(valorTotal);
    }

    @GetMapping("/token-validacao")
    public ResponseEntity<Void> getVendas(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        System.out.println("Token recebido: " + token);
        
    
        
        return ResponseEntity.ok().build(); 
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
