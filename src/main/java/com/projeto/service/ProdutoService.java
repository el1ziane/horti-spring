package com.projeto.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.projeto.dto.ProdutoDTO;
import com.projeto.dto.mapper.ProdutoMapper;
import com.projeto.exception.RecordNotFoundException;
import com.projeto.repository.ProdutoRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
@Validated
@SuppressWarnings("null")
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final ProdutoMapper produtoMapper;

    public ProdutoService(ProdutoRepository produtoRepository, ProdutoMapper produtoMapper) {
        this.produtoRepository = produtoRepository;
        this.produtoMapper = produtoMapper;
    }

    public List<ProdutoDTO> lista() {
        return produtoRepository.findAll()
                .stream()
                .map(produtoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProdutoDTO findById(@NotNull @Positive Long id) {
        return produtoRepository.findById(id).map(produtoMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public ProdutoDTO adicionar(@Valid @NotNull ProdutoDTO produto) {
        return produtoMapper.toDTO(produtoRepository.save(produtoMapper.toEntity(produto)));
    }

    public ProdutoDTO editar(@NotNull @Positive Long id, @Valid ProdutoDTO produto) {
        return produtoRepository.findById(id).map(registroEncontrado -> {
            registroEncontrado.setCodigo(produto.codigo());
            registroEncontrado.setProdutoNome(produto.produtoNome());
            registroEncontrado.setPeso(produto.peso());
            registroEncontrado.setQuantidadeProduto(produto.quantidadeProduto());
            registroEncontrado.setValorProduto(produto.valorProduto());
            registroEncontrado.setQuantidadePorCaixa(produto.quantidadePorCaixa());
            return produtoMapper.toDTO(produtoRepository.save(registroEncontrado));
        }).orElseThrow(() -> new RecordNotFoundException(id));

    }

    public void excluir(@NotNull @Positive Long id) {

        produtoRepository.delete(produtoRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));

    }
}
