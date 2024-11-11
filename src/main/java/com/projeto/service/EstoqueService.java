package com.projeto.service;

import java.util.stream.Collectors;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.projeto.dto.EstoqueDTO;
import com.projeto.dto.mapper.EstoqueMapper;
import com.projeto.exception.RecordNotFoundException;
import com.projeto.model.Estoque;
import com.projeto.model.Produto;
import com.projeto.repository.EstoqueRepository;
import com.projeto.repository.ProdutoRepository;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
@Validated
public class EstoqueService {
    private final EstoqueRepository estoqueRepository;
    private final EstoqueMapper estoqueMapper;
    private final ProdutoRepository produtoRepository;

    public EstoqueService(EstoqueRepository estoqueRepository, EstoqueMapper estoqueMapper,
            ProdutoRepository produtoRepository) {
        this.estoqueRepository = estoqueRepository;
        this.estoqueMapper = estoqueMapper;
        this.produtoRepository = produtoRepository;
    }

    public List<EstoqueDTO> lista() {
        return estoqueRepository.findAll()
                .stream()
                .map(estoqueMapper::toDTO)
                .collect(Collectors.toList());
    }

    public EstoqueDTO findById(@NotNull @Positive Long id) {
        return estoqueRepository.findById(id)
                .map(estoqueMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public Produto findByProdutoNome(@NotNull @NotBlank String nomeProduto) {
        return produtoRepository.findByProdutoNome(nomeProduto)
                .orElseThrow(() -> new RecordNotFoundException("Produto não encontrado!"));
    }

    public int getQuantidadeEstoque() {
        return estoqueRepository.findAll().stream()
                .mapToInt(Estoque::getQuantidadeEstoque)
                .sum();
    }

    public EstoqueDTO atualizarEstoque(EstoqueDTO estoqueDTO) {
        Estoque estoque = estoqueMapper.toEntity(estoqueDTO);
        // Aqui você deve buscar o produto e definir o valor unitário
        Produto produto = produtoRepository.findById(estoque.getProdutoEstoque().getId())
                .orElseThrow(() -> new RecordNotFoundException("Produto não encontrado!"));
        estoque.setValorUnitarioEstoque(produto.getValorProduto());
        estoque = estoqueRepository.save(estoque);
        return estoqueMapper.toDTO(estoque);
    }

    public EstoqueDTO removerQuantidadeEstoque(Long id, int quantidadeRemovida) {
        Estoque estoque = estoqueRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id));

        if (estoque.getQuantidadeEstoque() < quantidadeRemovida) {
            throw new IllegalArgumentException("Quantidade a ser removida é maior do que o estoque disponível.");
        }

        estoque.setQuantidadeEstoque(estoque.getQuantidadeEstoque() - quantidadeRemovida);
        estoque = estoqueRepository.save(estoque);
        return estoqueMapper.toDTO(estoque);
    }

}