package com.projeto.dto.mapper;

import org.springframework.stereotype.Component;

import com.projeto.dto.EstimativaDTO;
import com.projeto.enums.Status;
import com.projeto.model.Estimativa;

@Component
public class EstimativaMapper {
    public EstimativaDTO toDTO(Estimativa estimativa) {
        if (estimativa == null) {
            return null;
        }
        return new EstimativaDTO(estimativa.getId(), estimativa.getProdutoEstimativa(), estimativa.getPesoEstimativa(),
                estimativa.getCaixaEstimativa(), estimativa.getResultado(), estimativa.getTipoEntrada().getValue());
    }

    public Estimativa toEntity(EstimativaDTO estimativaDTO) {
        if (estimativaDTO == null) {
            return null;
        }
        Estimativa estimativa = new Estimativa();
        if (estimativaDTO.id() != null) {
            estimativa.setId(estimativaDTO.id());
        }
        estimativa.setProdutoEstimativa(estimativaDTO.produtoEstimativa());
        estimativa.setCaixaEstimativa(estimativaDTO.caixaEstimativa());
        estimativa.setPesoEstimativa(estimativaDTO.pesoEstimativa());
        estimativa.setTipoEntrada(convertStatusValue(estimativaDTO.tipoEntrada()));
        return estimativa;
    }

    public Status convertStatusValue(String value) {
        if (value == null) {
            return null;
        }
        return switch (value) {
            case "Peso" -> Status.PESO;
            case "Caixa" -> Status.CAIXA;
            default -> throw new IllegalArgumentException("Tipo inválido: " + value);
        };
    }
}
