package com.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.projeto.model.Estimativa;
import com.projeto.model.Produto;

public interface EstimativaRepository extends JpaRepository<Estimativa, Long> {

    void deleteByProdutoEstimativa(Produto produto);

}
