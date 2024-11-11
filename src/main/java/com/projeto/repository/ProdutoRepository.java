package com.projeto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{
    Optional<Produto> findByProdutoNome(String nomeProduto);

  
    
}
