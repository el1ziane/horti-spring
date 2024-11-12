package com.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.projeto.dto.FornecedorDTO;
import com.projeto.dto.mapper.FornecedorMapper;
import com.projeto.exception.DuplicateCnpjException;
import com.projeto.exception.RecordNotFoundException;
import com.projeto.model.Fornecedor;
import com.projeto.repository.FornecedorRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
@Validated
@SuppressWarnings("null")
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final FornecedorMapper fornecedorMapper;

    public FornecedorService(FornecedorRepository fornecedorRepository, FornecedorMapper fornecedorMapper) {
        this.fornecedorRepository = fornecedorRepository;
        this.fornecedorMapper = fornecedorMapper;
    }

    public List<FornecedorDTO> lista() {
        return fornecedorRepository.findAll()
                .stream()
                .map(fornecedorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public FornecedorDTO findById(@NotNull @Positive Long id) {
        return fornecedorRepository.findById(id).map(fornecedorMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public Fornecedor adicionar(@Valid @NotNull FornecedorDTO fornecedor) {
        fornecedorRepository.findByCnpj(fornecedor.cnpj())
        .ifPresent(existing -> {
            throw new DuplicateCnpjException("O CNPJ " + fornecedor.cnpj() + " já está cadastrado!");
        });
        Fornecedor f = new Fornecedor();
        f.setFornecedorNome(fornecedor.fornecedorNome());
        f.setCnpj(fornecedor.cnpj());
        f.setTelefone(fornecedor.telefone());
        f.setEmail(fornecedor.email());
        return fornecedorRepository.save(f);
    }

    public FornecedorDTO editar(@NotNull @Positive Long id, @Valid FornecedorDTO fornecedor) {
        return fornecedorRepository.findById(id).map(registroEncontrado -> {
            registroEncontrado.setFornecedorNome(fornecedor.fornecedorNome());
            registroEncontrado.setCnpj(fornecedor.cnpj());
            registroEncontrado.setTelefone(fornecedor.telefone());
            registroEncontrado.setEmail(fornecedor.email());
            return fornecedorMapper.toDTO(fornecedorRepository.save(registroEncontrado));
        }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void excluir(@NotNull @Positive Long id) {

        fornecedorRepository
                .delete(fornecedorRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));

    }
}
