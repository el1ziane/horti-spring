package com.projeto.service;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.projeto.dto.EstimativaDTO;
import com.projeto.dto.mapper.EstimativaMapper;
import com.projeto.enums.Status;
import com.projeto.exception.RecordNotFoundException;
import com.projeto.model.Estimativa;
import com.projeto.repository.EstimativaRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
@Validated
@SuppressWarnings("null")
public class EstimativaService {
    private final EstimativaRepository estimativaRepository;
    private final EstimativaMapper estimativaMapper;

    public EstimativaService(EstimativaRepository estimativaRepository, EstimativaMapper estimativaMapper){
        this.estimativaRepository = estimativaRepository;
        this.estimativaMapper = estimativaMapper;
    }

    public List<EstimativaDTO> lista() {
        return estimativaRepository.findAll()
        .stream()
        .map(estimativaMapper::toDTO)
        .collect(Collectors.toList());
    }

    public EstimativaDTO findById(@NotNull @Positive Long id){
        return estimativaRepository.findById(id).map(estimativaMapper::toDTO)
        .orElseThrow(()-> new RecordNotFoundException(id));
    }

    public EstimativaDTO adicionar( @Valid @NotNull EstimativaDTO estimativa){
        return estimativaMapper.toDTO(estimativaRepository.save(estimativaMapper.toEntity(estimativa)));
    }

    public Estimativa calcular(@Valid Estimativa estimativa) {
        if (estimativa.getTipoEntrada().equals(Status.PESO)) {
            if (estimativa.getPesoEstimativa() == null) {
                throw new IllegalArgumentException("Peso estimativa deve ser informado para tipo de entrada PESO");
            }
            estimativa.setCaixaEstimativa(null); // Garante que não será inserido um valor inválido para caixa
        } else if (estimativa.getTipoEntrada().equals(Status.CAIXA)) {
            if (estimativa.getCaixaEstimativa() == null) {
                throw new IllegalArgumentException("Caixa estimativa deve ser informada para tipo de entrada CAIXA");
            }
            estimativa.setPesoEstimativa(null); // Garante que não será inserido um valor inválido para peso
        }
    
        // Calcular o resultado, por exemplo
        estimativa.setResultado(calcularResultado(estimativa));
    
        return estimativaRepository.save(estimativa);
    }
    
    private Double calcularResultado(Estimativa estimativa) {
        if (estimativa.getProdutoEstimativa() == null) {
            throw new IllegalArgumentException("Produto deve estar associado à estimativa.");
        }
        if (estimativa.getTipoEntrada().equals(Status.PESO)) {
            return estimativa.getPesoEstimativa() / estimativa.getProdutoEstimativa().getPeso();
        } else if (estimativa.getTipoEntrada().equals(Status.CAIXA)) {
            return (double) (estimativa.getCaixaEstimativa() * estimativa.getProdutoEstimativa().getQuantidadePorCaixa());
        }
        return 0.0;
    }
    

}
