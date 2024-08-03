package com.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.projeto.dto.ProducaoDTO;
import com.projeto.dto.mapper.ProducaoMapper;
import com.projeto.exception.RecordNotFoundException;
import com.projeto.repository.ProducaoRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
@Validated
@SuppressWarnings("null")
public class ProducaoService {
    
    private final ProducaoRepository producaoRepository; 
    private final ProducaoMapper producaoMapper;

    public ProducaoService(ProducaoRepository producaoRepository, ProducaoMapper producaoMapper){
        this.producaoRepository = producaoRepository;
        this.producaoMapper = producaoMapper;
    }
    public List<ProducaoDTO> lista() {
        return producaoRepository.findAll()
        .stream()
        .map(producaoMapper::toDTO)
        .collect(Collectors.toList());
    }
    public ProducaoDTO findById(@NotNull @Positive Long id){
        return producaoRepository.findById(id).map(producaoMapper::toDTO)
        .orElseThrow(()-> new RecordNotFoundException(id));
    }
     public ProducaoDTO adicionar( @Valid @NotNull ProducaoDTO producao){
        return producaoMapper.toDTO(producaoRepository.save(producaoMapper.toEntity(producao)));
     }
     public ProducaoDTO editar(@NotNull @Positive Long id, @Valid ProducaoDTO producao){
        return producaoRepository.findById(id).map(registroEncontrado ->{
            registroEncontrado.setCodigo(producao.codigo());
            return producaoMapper.toDTO(producaoRepository.save(registroEncontrado));
        }).orElseThrow(()-> new RecordNotFoundException(id));

    }
    public void excluir(@NotNull @Positive Long id){

        producaoRepository.delete(producaoRepository.findById(id).orElseThrow(()-> new RecordNotFoundException(id)));

    }
}
