package com.projeto.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.projeto.dto.EstimativaDTO;
import com.projeto.model.Estimativa;
import com.projeto.service.EstimativaService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/api/estimativa")
@CrossOrigin(origins = "http://localhost:4200")
public class EstimativaController {

    private final EstimativaService estimativaService;

    public EstimativaController(EstimativaService estimativaService) {
        this.estimativaService = estimativaService;
    }

    @GetMapping
    public List<EstimativaDTO> lista() {
        return estimativaService.lista();
    }

    @GetMapping("/{id}")
    public EstimativaDTO findById(@PathVariable @NotNull @Positive Long id) {
        return estimativaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Estimativa calcular(@RequestBody @Valid Estimativa estimativa) {
        return estimativaService.calcular(estimativa);
    }
}