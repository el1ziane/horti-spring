package com.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.annotation.Validated;

import com.projeto.dto.VendaDTO;
import com.projeto.dto.mapper.VendaMapper;
import com.projeto.exception.RecordNotFoundException;
import com.projeto.model.Produto;
import com.projeto.model.Venda;
import com.projeto.repository.ProdutoRepository;
import com.projeto.repository.VendaRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
@Validated
@SuppressWarnings("null")
public class VendaService {

    private final VendaRepository vendaRepository;
    private final VendaMapper vendaMapper;
    private final ProdutoRepository produtoRepository;

    public VendaService(VendaRepository vendaRepository, VendaMapper vendaMapper, ProdutoRepository produtoRepository) {
        this.vendaRepository = vendaRepository;
        this.vendaMapper = vendaMapper;
        this.produtoRepository = produtoRepository;
    }

    public List<VendaDTO> lista() {
        return vendaRepository.findAll()
                .stream()
                .map(vendaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public VendaDTO findById(@NotNull @Positive Long id) {
        return vendaRepository.findById(id).map(vendaMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public Produto findByProdutoNome(@NotNull @NotBlank String produtoNome) {
        return produtoRepository.findByProdutoNome(produtoNome)
                .orElseThrow(() -> new RecordNotFoundException("Produto não encontrado!"));
    }

    public double getValorTotalVendas() {
        return vendaRepository.findAll().stream()
                .mapToDouble(Venda::getValorVenda) // Altere 'getValor' para o método que retorna o valor da venda
                .sum();
    }

    public VendaDTO adicionar(@Valid @NotNull VendaDTO vendaDTO) {
        System.out.println("VendaDTO recebido: " + vendaDTO);

        // Obter o produto a partir do nome
        Produto produto = produtoRepository.findByProdutoNome(vendaDTO.produtoVenda().getProdutoNome())
                .orElseThrow(() -> new RecordNotFoundException("Produto não encontrado!"));

        // Calcular o valor total
        double valorTotal = vendaDTO.valorVenda() * vendaDTO.quantidadeVenda();

        // Mapear para a entidade Venda
        Venda venda = vendaMapper.toEntity(vendaDTO);
        venda.setValorVenda(valorTotal); // Armazenar o valor total na venda

        // Associar o produto à venda
        venda.setProdutoVenda(produto); // Certifique-se de ter um método setProduto na entidade Venda

        try {
            Venda vendaSalva = vendaRepository.save(venda);
            return vendaMapper.toDTO(vendaSalva);
        } catch (TransactionSystemException e) {
            Throwable cause = e.getCause();
            if (cause != null) {
                System.err.println("Causa da exceção: " + cause.getMessage());
            }
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar a venda", e);
        }
    }

    public VendaDTO editar(@NotNull @Positive Long id, @Valid VendaDTO venda) {
        return vendaRepository.findById(id).map(registroEncontrado -> {
            // Calcular o novo valor total
            double novoValorTotal = venda.valorVenda() * venda.quantidadeVenda();

            registroEncontrado.setCodigoVenda(venda.codigoVenda());
            registroEncontrado.setProdutoVenda(venda.produtoVenda());
            registroEncontrado.setQuantidadeVenda(venda.quantidadeVenda());
            registroEncontrado.setValorVenda(novoValorTotal); // Atualiza o valor com o total calculado
            registroEncontrado.setDataInicial(venda.dataInicial());
            registroEncontrado.setDataFinal(venda.dataFinal());
            registroEncontrado.setStatusVenda(vendaMapper.convertStatusValue(venda.statusVenda()));

            return vendaMapper.toDTO(vendaRepository.save(registroEncontrado));
        }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void excluir(@NotNull @Positive Long id) {

        vendaRepository.delete(vendaRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id)));

    }

}
