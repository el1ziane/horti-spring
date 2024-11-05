package com.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.model.Estoque;
import com.projeto.model.Producao;
import com.projeto.model.Produto;
import com.projeto.model.Venda;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    Optional<Produto> findByProdutoNome(String nomeProduto);

  
    
}
