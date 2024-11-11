package com.projeto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.model.Faturamento;
import com.projeto.service.FaturamentoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/faturamento")
public class FaturamentoController {
    @Autowired
    private FaturamentoService faturamentoService;

    @GetMapping("/mes/{mesAno}")
    public List<Faturamento> getFinanceiroPorMes(@PathVariable String mesAno) {
        return faturamentoService.gerarFinanceiroPorMes(mesAno);
    }
}