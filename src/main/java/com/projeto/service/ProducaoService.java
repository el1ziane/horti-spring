package com.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.projeto.dto.EstoqueDTO;
import com.projeto.dto.ProducaoDTO;
import com.projeto.dto.mapper.EstoqueMapper;
import com.projeto.dto.mapper.ProducaoMapper;
import com.projeto.exception.RecordNotFoundException;
import com.projeto.model.Estoque;
import com.projeto.model.Producao;
import com.projeto.repository.EstoqueRepository;
import com.projeto.repository.ProducaoRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
@Validated
@SuppressWarnings("null")
public class ProducaoService {
    
    private final ProducaoRepository producaoRepository; 
    private final EstoqueService estoqueService;
    private final EstoqueMapper estoqueMapper;
    private final ProducaoMapper producaoMapper;

    public ProducaoService(ProducaoRepository producaoRepository, ProducaoMapper producaoMapper, EstoqueService estoqueService, EstoqueMapper estoqueMapper) {
        this.estoqueService = estoqueService;
        this.estoqueMapper = estoqueMapper;
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
    /*public Producao adicionar(@Valid @NotNull ProducaoDTO producao){
        Producao p = new Producao();
        p.setCodigoProducao(producao.codigoProducao());
        p.setProdutoProducao(producao.produtoProducao());
        p.setQuantidadeProducao(producao.quantidadeProducao());
        p.setDataProducao(producao.dataProducao());

        return producaoRepository.save(p);
        
     
    }*/
    public Producao adicionar(@Valid @NotNull ProducaoDTO producao){
        Producao p = new Producao();
        p.setCodigoProducao(producao.codigoProducao());
        p.setProdutoProducao(producao.produtoProducao());
        p.setQuantidadeProducao(producao.quantidadeProducao());
        p.setDataProducao(producao.dataProducao());

        // Salva a produção no banco de dados
       Producao savedProducao = producaoRepository.save(p);
       //return producaoRepository.save(p);
        // Agora, atualiza o estoque com a nova produção
        Estoque novoEstoque = new Estoque();
        novoEstoque.setCodigoEstoque(savedProducao.getCodigoProducao()); // Use um código apropriado
        novoEstoque.setProdutoEstoque(savedProducao.getProdutoProducao());
        novoEstoque.setQuantidadeEstoque(savedProducao.getQuantidadeProducao());
        novoEstoque.setValorUnitarioEstoque(calcularValorUnitario(savedProducao)); // Defina essa lógica conforme sua necessidade
        novoEstoque.setValorTotalEstoque(novoEstoque.getQuantidadeEstoque() * novoEstoque.getValorUnitarioEstoque());
        novoEstoque.setDataEstoque(savedProducao.getDataProducao());

        // Salva no estoque
        estoqueService.adicionarEstoque(estoqueMapper.toDTO(novoEstoque));

        return savedProducao;
    }
    private double calcularValorUnitario(Producao producao) {
        // Implementar a lógica para calcular o valor unitário
        return 0; // Alterar de acordo com a lógica
    }

    public ProducaoDTO editar(@NotNull @Positive Long id, @Valid ProducaoDTO producao){
        return producaoRepository.findById(id).map(registroEncontrado -> {
         

            registroEncontrado.setCodigoProducao(producao.codigoProducao());
            registroEncontrado.setQuantidadeProducao(producao.quantidadeProducao());
            registroEncontrado.setProdutoProducao(producao.produtoProducao());
            registroEncontrado.setDataProducao(producao.dataProducao());

        
            return producaoMapper.toDTO(producaoRepository.save(registroEncontrado));
        }).orElseThrow(() -> new RecordNotFoundException(id));
    }

 
    public void excluir(@NotNull @Positive Long id) {
        producaoRepository.findById(id).ifPresent(producao -> {
       
            producaoRepository.delete(producao);
        });
    }


}

