package com.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.projeto.dto.CompraDTO;

import com.projeto.dto.mapper.CompraMapper;

import com.projeto.enums.Status;
import com.projeto.exception.RecordNotFoundException;
import com.projeto.model.Compra;
import com.projeto.repository.CompraRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
@Validated
@SuppressWarnings("null")
public class CompraService {

    private final CompraRepository compraRepository;
    private final CompraMapper compraMapper;

    public CompraService(CompraRepository compraRepository, CompraMapper compraMapper) {
        this.compraRepository = compraRepository;
        this.compraMapper = compraMapper;
    }

    public List<CompraDTO> lista() {
        return compraRepository.findAll()
                .stream()
                .map(compraMapper::toDTO)
                .collect(Collectors.toList());
    }

    public CompraDTO findById(@NotNull @Positive Long id) {
        return compraRepository.findById(id).map(compraMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public double getTotalComprasPendentes() {
        return compraRepository.findAll().stream()
                .filter(compra -> {
                    // System.out.println("Valor: " + recebimento.getValorRecebimento());
                    return Status.PENDENTE.equals(compra.getStatus());
                })
                .mapToDouble(Compra::getValorCompra)
                .sum();
    }

    public CompraDTO adicionar(@Valid @NotNull CompraDTO compra) {
        return compraMapper.toDTO(compraRepository.save(compraMapper.toEntity(compra)));
    }

    public CompraDTO editar(@NotNull @Positive Long id, @Valid CompraDTO compra) {
        return compraRepository.findById(id).map(registroEncontrado -> {
            registroEncontrado.setFornecedorCompra(compra.fornecedorCompra());
            registroEncontrado.setProdutoCompra(compra.produtoCompra());
            registroEncontrado.setQuantidadeCompra(compra.quantidadeCompra());
            registroEncontrado.setValorCompra(compra.valorCompra());
            registroEncontrado.setDataCompra(compra.dataCompra());
            registroEncontrado.setStatus(compraMapper.convertStatusValue(compra.status()));
            return compraMapper.toDTO(compraRepository.save(registroEncontrado));
        }).orElseThrow(() -> new RecordNotFoundException(id));

    }

    public void excluir(@NotNull @Positive Long id) {

        compraRepository.delete(compraRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));

    }

}
