package com.projeto.service;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.projeto.dto.EstoqueDTO;
import com.projeto.dto.mapper.EstoqueMapper;
import com.projeto.exception.RecordNotFoundException;
import com.projeto.model.Estoque;
import com.projeto.repository.EstoqueRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
@Validated
public class EstoqueService {
    private final EstoqueRepository estoqueRepository; 
    private final EstoqueMapper estoqueMapper;

    public EstoqueService(EstoqueRepository estoqueRepository, EstoqueMapper estoqueMapper){
        this.estoqueRepository = estoqueRepository;
        this.estoqueMapper = estoqueMapper;
    }

    public List<EstoqueDTO> lista() {
        return estoqueRepository.findAll()
        .stream()
        .map(estoqueMapper::toDTO)
        .collect(Collectors.toList());
    }
 public EstoqueDTO adicionarEstoque(EstoqueDTO estoqueDTO) {
        Estoque estoque = estoqueMapper.toEntity(estoqueDTO);
        estoque = estoqueRepository.save(estoque);
        return estoqueMapper.toDTO(estoque);
    }
    public EstoqueDTO findById(@NotNull @Positive Long id) {
        return estoqueRepository.findById(id)
            .map(estoqueMapper::toDTO)
            .orElseThrow(() -> new RecordNotFoundException(id));
    }


    public void excluir(@NotNull @Positive Long id) {
        estoqueRepository.delete(estoqueRepository.findById(id)
            .orElseThrow(() -> new RecordNotFoundException(id)));
    }
}
