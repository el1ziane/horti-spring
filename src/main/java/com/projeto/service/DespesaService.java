package com.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.projeto.dto.DespesaDTO;
import com.projeto.dto.mapper.DespesaMapper;
import com.projeto.exception.RecordNotFoundException;
import com.projeto.model.Despesa;
import com.projeto.model.Recebimento;
import com.projeto.repository.DespesaRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.projeto.enums.Status;

@Service
@Validated
@SuppressWarnings("null")
public class DespesaService {
    
    private final DespesaRepository despesaRepository; 
    private final DespesaMapper despesaMapper;

    public DespesaService(DespesaRepository despesaRepository, DespesaMapper despesaMapper){
        this.despesaRepository = despesaRepository;
        this.despesaMapper = despesaMapper;
    }
    public List<DespesaDTO> lista() {
        return despesaRepository.findAll()
        .stream()
        .map(despesaMapper::toDTO)
        .collect(Collectors.toList());
    }
    public DespesaDTO findById(@NotNull @Positive Long id){
        return despesaRepository.findById(id).map(despesaMapper::toDTO)
        .orElseThrow(()-> new RecordNotFoundException(id));
    }
    public double getTotalDespesaPendentes() {
    return despesaRepository.findAll().stream()
        .filter(despesa -> {
           // System.out.println("Valor: " + despesa.getValorDespesa());
            return Status.PENDENTE.equals(despesa.getStatusDespesa()); // Comparando com a enumeração
        })
        .mapToDouble(Despesa::getValorDespesa)
        .sum();
}

    
     public Despesa adicionar( @Valid @NotNull DespesaDTO despesa){
        Despesa d = new Despesa();
        d.setFornecedorDespesa(despesa.fornecedorDespesa());
        d.setDescricaoDespesa(despesa.descricaoDespesa());
        d.setValorDespesa(despesa.valorDespesa());
        d.setDataDespesa(despesa.dataDespesa());
        d.setStatusDespesa(despesaMapper.convertStatusValue(despesa.statusDespesa()));
        return despesaRepository.save(d);
     }
     public DespesaDTO editar(@NotNull @Positive Long id, @Valid DespesaDTO despesa){
        return despesaRepository.findById(id).map(registroEncontrado ->{
            registroEncontrado.setFornecedorDespesa(despesa.fornecedorDespesa());
            registroEncontrado.setDescricaoDespesa(despesa.descricaoDespesa());
            registroEncontrado.setValorDespesa(despesa.valorDespesa());
            registroEncontrado.setDataDespesa(despesa.dataDespesa());
            registroEncontrado.setStatusDespesa(despesaMapper.convertStatusValue(despesa.statusDespesa()));
            return despesaMapper.toDTO(despesaRepository.save(registroEncontrado));
        }).orElseThrow(()-> new RecordNotFoundException(id));
     }
    public void excluir(@NotNull @Positive Long id){

        despesaRepository.delete(despesaRepository.findById(id).orElseThrow(()-> new RecordNotFoundException(id)));

    }
}
