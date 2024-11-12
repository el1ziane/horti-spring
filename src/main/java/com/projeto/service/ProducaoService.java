package com.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.validation.annotation.Validated;

import com.projeto.dto.ProducaoDTO;
import com.projeto.dto.mapper.ProducaoMapper;
import com.projeto.exception.RecordNotFoundException;
import com.projeto.model.Estoque;
import com.projeto.model.Producao;
import com.projeto.model.Produto;
import com.projeto.repository.EstoqueRepository;
import com.projeto.repository.ProducaoRepository;
import com.projeto.repository.ProdutoRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
@Validated
@SuppressWarnings("null")
public class ProducaoService {

    private final ProducaoRepository producaoRepository;
    private final ProducaoMapper producaoMapper;
    private final ProdutoRepository produtoRepository;
    private final EstoqueRepository estoqueRepository;

    public ProducaoService(ProducaoRepository producaoRepository, ProducaoMapper producaoMapper,
            ProdutoRepository produtoRepository, EstoqueRepository estoqueRepository) {
        this.estoqueRepository = estoqueRepository;
        this.produtoRepository = produtoRepository;
        this.producaoMapper = producaoMapper;
        this.producaoRepository = producaoRepository;

    }

    public List<ProducaoDTO> lista() {
        return producaoRepository.findAll()
                .stream()
                .map(producaoMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProducaoDTO findById(@NotNull @Positive Long id) {
        return producaoRepository.findById(id).map(producaoMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public Produto findByProdutoNome(@NotNull @NotBlank String produtoNome) {
        return produtoRepository.findByProdutoNome(produtoNome)
                .orElseThrow(() -> new RecordNotFoundException("Produto não encontrado!"));
    }

    public ProducaoDTO adicionar(@Valid @NotNull ProducaoDTO producaoDTO) {
        Produto produto = produtoRepository.findByProdutoNome(producaoDTO.produtoProducao().getProdutoNome())
                .orElseThrow(() -> new RecordNotFoundException("Produto não encontrado!"));

        Producao producao = producaoMapper.toEntity(producaoDTO);
        producao.setCodigoProducao(produto.getCodigo());

        Producao producaoSalva = producaoRepository.save(producao);

        Estoque estoque = estoqueRepository.findByCodigoEstoque(produto.getCodigo())
                .orElse(new Estoque());

        estoque.setCodigoEstoque(produto.getCodigo());

        Integer quantidadeAtual = estoque.getQuantidadeEstoque();
        if (quantidadeAtual == null) {
            quantidadeAtual = 0;
        }

        estoque.setQuantidadeEstoque(quantidadeAtual + producaoDTO.quantidadeProducao());
        estoque.setDataEstoque(producaoDTO.dataProducao());
        estoque.setProdutoEstoque(produto);
        estoque.setValorUnitarioEstoque(produto.getValorProduto());

        estoqueRepository.save(estoque);

        return producaoMapper.toDTO(producaoSalva);
    }

    public ProducaoDTO editar(@NotNull @Positive Long id, @Valid ProducaoDTO producaoDTO) {
        return producaoRepository.findById(id).map(registroEncontrado -> {

            Produto produto = produtoRepository.findByProdutoNome(producaoDTO.produtoProducao().getProdutoNome())
                    .orElseThrow(() -> new RecordNotFoundException("Produto não encontrado!"));

            Integer quantidadeAntiga = registroEncontrado.getQuantidadeProducao();
            registroEncontrado.setCodigoProducao(producaoDTO.codigoProducao());
            registroEncontrado.setQuantidadeProducao(producaoDTO.quantidadeProducao());
            registroEncontrado.setProdutoProducao(producaoDTO.produtoProducao());
            registroEncontrado.setDataProducao(producaoDTO.dataProducao());

            Estoque estoque = estoqueRepository.findByCodigoEstoque(produto.getCodigo())
                    .orElseThrow(() -> new RecordNotFoundException("Estoque não encontrado para o produto."));

            Integer novaQuantidade = producaoDTO.quantidadeProducao();

            estoque.setQuantidadeEstoque(estoque.getQuantidadeEstoque() - quantidadeAntiga + novaQuantidade);
            estoque.setDataEstoque(producaoDTO.dataProducao());
            estoque.setProdutoEstoque(produto);

            estoque.setValorUnitarioEstoque(produto.getValorProduto());

            double valorTotal = estoque.getQuantidadeEstoque() * estoque.getValorUnitarioEstoque();
            estoque.setValorTotalEstoque(valorTotal);
            estoqueRepository.save(estoque);

            return producaoMapper.toDTO(producaoRepository.save(registroEncontrado));
        }).orElseThrow(() -> new RecordNotFoundException(id));
    }
    @Transactional
    public void excluir(@NotNull @Positive Long id) {
        producaoRepository.findById(id).ifPresent(producao -> {

            Produto produto = produtoRepository.findByProdutoNome(producao.getProdutoProducao().getProdutoNome())
                    .orElseThrow(() -> new RecordNotFoundException("Produto não encontrado!"));

            Estoque estoque = estoqueRepository.findByCodigoEstoque(produto.getCodigo())
                    .orElseThrow(() -> new RecordNotFoundException("Estoque não encontrado para o produto."));

            Integer quantidadeProducao = producao.getQuantidadeProducao();
            estoque.setQuantidadeEstoque(estoque.getQuantidadeEstoque() - quantidadeProducao);

            double valorTotal = estoque.getQuantidadeEstoque() * estoque.getValorUnitarioEstoque();
            estoque.setValorTotalEstoque(valorTotal);

            estoqueRepository.save(estoque);

            producaoRepository.delete(producao);
        });
    }

}
