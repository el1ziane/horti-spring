package com.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
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
    
    public VendaService(VendaRepository vendaRepository, VendaMapper vendaMapper, ProdutoRepository produtoRepository){
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
    public VendaDTO findById(@NotNull @Positive Long id){
        return vendaRepository.findById(id).map(vendaMapper::toDTO)
        .orElseThrow(()-> new RecordNotFoundException(id));
    }
    public Produto findByProdutoNome(@NotNull @NotBlank String nomeProduto) {
        return produtoRepository.findByProdutoNome(nomeProduto)
                .orElseThrow(() -> new RecordNotFoundException("Produto não encontrado!"));
    }
    public double getValorTotalVendas() {
        return vendaRepository.findAll().stream()
                .mapToDouble(Venda::getValorVenda) // Altere 'getValor' para o método que retorna o valor da venda
                .sum();
    }
    
       public VendaDTO adicionar(@Valid @NotNull VendaDTO vendaDTO) {
        Produto produto = produtoRepository.findByProdutoNome(vendaDTO.produtoVenda())
                .orElseThrow(() -> new RecordNotFoundException("Produto não encontrado!"));

        Venda v = vendaMapper.toEntity(vendaDTO);

        v.setCodigoVenda(produto.getCodigo());
       

        Venda vendaSalva = vendaRepository.save(v);
        return vendaMapper.toDTO(vendaSalva);
        //return vendaRepository.save(v);
    }
     public VendaDTO editar(@NotNull @Positive Long id, @Valid VendaDTO venda){
        return vendaRepository.findById(id).map(registroEncontrado ->{

            registroEncontrado.setCodigoVenda(venda.codigoVenda());
            registroEncontrado.setProdutoVenda(venda.produtoVenda());
            registroEncontrado.setQuantidadeVenda(venda.quantidadeVenda());
            registroEncontrado.setValorVenda(venda.valorVenda());
            registroEncontrado.setDataInicial(venda.dataInicial());
            registroEncontrado.setDataFinal(venda.dataFinal());
            registroEncontrado.setStatusVenda(vendaMapper.convertStatusValue(venda.statusVenda()));

           return vendaMapper.toDTO(vendaRepository.save(registroEncontrado));

          // Venda vendaAtualizada = vendaRepository.save(registroEncontrado);
         //  return vendaMapper.toDTO(vendaAtualizada);

        }).orElseThrow(()-> new RecordNotFoundException(id));

    }
    public void excluir(@NotNull @Positive Long id){

        vendaRepository.delete(vendaRepository.findById(id).orElseThrow(()-> new RecordNotFoundException(id)));

    }

}
