package com.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
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

    public ProducaoService(ProducaoRepository producaoRepository, ProducaoMapper producaoMapper, ProdutoRepository produtoRepository, EstoqueRepository estoqueRepository) {
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
    public ProducaoDTO findById(@NotNull @Positive Long id){
        return producaoRepository.findById(id).map(producaoMapper::toDTO)
        .orElseThrow(()-> new RecordNotFoundException(id));
    }
    public Produto findByProdutoNome(@NotNull @NotBlank String produtoNome) {
        return produtoRepository.findByProdutoNome(produtoNome)
                .orElseThrow(() -> new RecordNotFoundException("Produto não encontrado!"));
    }    
    
   
    public ProducaoDTO adicionar(@Valid @NotNull ProducaoDTO producaoDTO) {
        // Recupera o produto pelo nome
        Produto produto = produtoRepository.findByProdutoNome(producaoDTO.produtoProducao().getProdutoNome())
                .orElseThrow(() -> new RecordNotFoundException("Produto não encontrado!"));
    
     
        Producao producao = producaoMapper.toEntity(producaoDTO);
        producao.setCodigoProducao(produto.getCodigo());
    
        Producao producaoSalva = producaoRepository.save(producao);
    
        // Verifica se já existe um estoque para o produto
        Estoque estoque = estoqueRepository.findByCodigoEstoque(produto.getCodigo())
                .orElse(new Estoque()); // Se não existir, cria um novo objeto Estoque
    
        // Atualiza ou cria uma nova entrada no estoque
        estoque.setCodigoEstoque(produto.getCodigo());
        
      // Aqui usamos Integer para garantir que possa ser null
Integer quantidadeAtual = estoque.getQuantidadeEstoque();
if (quantidadeAtual == null) {
    quantidadeAtual = 0;
}

estoque.setQuantidadeEstoque(quantidadeAtual + producaoDTO.quantidadeProducao());
estoque.setDataEstoque(producaoDTO.dataProducao());
estoque.setProdutoEstoque(produto);  // Usa o produto que foi produzido

// Atribui o valor unitário do produto ao estoque
estoque.setValorUnitarioEstoque(produto.getValorProduto()); // Certifique-se de que o valor do produto está correto

    
        // Salva ou atualiza o estoque
        estoqueRepository.save(estoque);
    
        return producaoMapper.toDTO(producaoSalva); // Retorna o DTO da produção salva
    }
    
    

    public ProducaoDTO editar(@NotNull @Positive Long id, @Valid ProducaoDTO producaoDTO) {
        return producaoRepository.findById(id).map(registroEncontrado -> {
            
            // Obtém o produto associado à produção
            Produto produto = produtoRepository.findByProdutoNome(producaoDTO.produtoProducao().getProdutoNome())
            .orElseThrow(() -> new RecordNotFoundException("Produto não encontrado!"));
            
            // Atualiza os dados da produção
            Integer quantidadeAntiga = registroEncontrado.getQuantidadeProducao();
            registroEncontrado.setCodigoProducao(producaoDTO.codigoProducao());
            registroEncontrado.setQuantidadeProducao(producaoDTO.quantidadeProducao());
            registroEncontrado.setProdutoProducao(producaoDTO.produtoProducao());
            registroEncontrado.setDataProducao(producaoDTO.dataProducao());

              // Verifica se já existe um estoque para o produto
              Estoque estoque = estoqueRepository.findByCodigoEstoque(produto.getCodigo())
              .orElseThrow(() -> new RecordNotFoundException("Estoque não encontrado para o produto."));
      
      // Atualiza a quantidade no estoque
      Integer novaQuantidade = producaoDTO.quantidadeProducao();
      
      // Atualiza o estoque: soma ou subtrai conforme a nova quantidade
      estoque.setQuantidadeEstoque(estoque.getQuantidadeEstoque() - quantidadeAntiga + novaQuantidade);
      estoque.setDataEstoque(producaoDTO.dataProducao());
      estoque.setProdutoEstoque(produto); // Usa o produto que foi produzido
      
        
// Atribui o valor unitário do produto ao estoque
estoque.setValorUnitarioEstoque(produto.getValorProduto()); // Assegura que o valor unitário do produto é atribuído

// Calcula o valor total (quantidade * valor unitário)
double valorTotal = estoque.getQuantidadeEstoque() * estoque.getValorUnitarioEstoque();
estoque.setValorTotalEstoque(valorTotal);
      // Salva as atualizações do estoque
      estoqueRepository.save(estoque);

            return producaoMapper.toDTO(producaoRepository.save(registroEncontrado));
        }).orElseThrow(() -> new RecordNotFoundException(id));
            // Salva a produção atualizada
            //Producao producaoAtualizada = producaoRepository.save(registroEncontrado);
            
          
            
           
    }
    
        
 
    public void excluir(@NotNull @Positive Long id) {
        producaoRepository.findById(id).ifPresent(producao -> {
            // Encontrar o produto associado à produção
            Produto produto = produtoRepository.findByProdutoNome(producao.getProdutoProducao().getProdutoNome())
                    .orElseThrow(() -> new RecordNotFoundException("Produto não encontrado!"));
    
            // Verificar se existe estoque associado ao produto
            Estoque estoque = estoqueRepository.findByCodigoEstoque(produto.getCodigo())
                    .orElseThrow(() -> new RecordNotFoundException("Estoque não encontrado para o produto."));
    
            // Subtrair a quantidade da produção do estoque
            Integer quantidadeProducao = producao.getQuantidadeProducao();
            estoque.setQuantidadeEstoque(estoque.getQuantidadeEstoque() - quantidadeProducao);
    
            // Atualizar o valor total do estoque
            double valorTotal = estoque.getQuantidadeEstoque() * estoque.getValorUnitarioEstoque();
            estoque.setValorTotalEstoque(valorTotal);
    
            // Salvar o estoque atualizado
            estoqueRepository.save(estoque);
    
            // Excluir a produção
            producaoRepository.delete(producao);
        });
    }
    


}

