package com.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projeto.model.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Long> {
    @Query("SELECT SUM(d.valorDespesa) FROM Despesa d WHERE d.statusDespesa = :status")
    Double findTotalByStatus(@Param("status") String status);
}
