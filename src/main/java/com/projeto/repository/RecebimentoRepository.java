package com.projeto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.projeto.model.Recebimento;


public interface RecebimentoRepository extends JpaRepository<Recebimento, Long>{
@Query("SELECT SUM(r.valorRecebimento) FROM Recebimento r WHERE r.status = :status")
    Double findTotalByStatus(@Param("status") String status);
    
    
} 
