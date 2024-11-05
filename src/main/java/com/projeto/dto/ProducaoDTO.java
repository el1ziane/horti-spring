package com.projeto.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.projeto.model.Produto;

import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.NotNull;


public record ProducaoDTO(

    @JsonProperty("_id") Long id,
    @NotNull @Min(10) Long codigoProducao,
    @NotNull int quantidadeProducao,
    Produto produtoProducao,
@NotNull
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
LocalDate dataProducao
    ) {
        
    }

