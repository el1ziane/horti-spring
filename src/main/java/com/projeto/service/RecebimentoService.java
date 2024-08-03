package com.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.projeto.dto.RecebimentoDTO;
import com.projeto.dto.mapper.RecebimentoMapper;
import com.projeto.exception.RecordNotFoundException;
import com.projeto.repository.RecebimentoRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
@Validated
@SuppressWarnings("null")
public class RecebimentoService {

    private final RecebimentoRepository recebimentoRepository; 
    private final RecebimentoMapper recebimentoMapper;

    public RecebimentoService(RecebimentoRepository recebimentoRepository, RecebimentoMapper recebimentoMapper){
        this.recebimentoRepository = recebimentoRepository;
        this.recebimentoMapper = recebimentoMapper;
    }
    public List<RecebimentoDTO> lista() {
        return recebimentoRepository.findAll()
        .stream()
        .map(recebimentoMapper::toDTO)
        .collect(Collectors.toList());
    }
    public RecebimentoDTO findById(@NotNull @Positive Long id){
        return recebimentoRepository.findById(id).map(recebimentoMapper::toDTO)
        .orElseThrow(()-> new RecordNotFoundException(id));
    }
     public RecebimentoDTO adicionar( @Valid @NotNull RecebimentoDTO recebimento){
        return recebimentoMapper.toDTO(recebimentoRepository.save(recebimentoMapper.toEntity(recebimento)));
     }
     public RecebimentoDTO editar(@NotNull @Positive Long id, @Valid RecebimentoDTO recebimento){
        return recebimentoRepository.findById(id).map(registroEncontrado ->{
            registroEncontrado.setFornecedorRec(recebimento.fornecedorRec());
            registroEncontrado.setProduto(recebimento.produto());
            registroEncontrado.setQuantidadeRecebimento(recebimento.quantidadeRecebimento());
            registroEncontrado.setValorRecebimento(recebimento.valorRecebimento());
            registroEncontrado.setDataRecebimento(recebimento.dataRecebimento());
            registroEncontrado.setStatus(recebimentoMapper.convertStatusValue(recebimento.status()));
            return recebimentoMapper.toDTO(recebimentoRepository.save(registroEncontrado));
        }).orElseThrow(()-> new RecordNotFoundException(id));

    }
    public void excluir(@NotNull @Positive Long id){

        recebimentoRepository.delete(recebimentoRepository.findById(id).orElseThrow(()-> new RecordNotFoundException(id)));

    }

}
