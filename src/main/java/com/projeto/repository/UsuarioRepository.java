package com.projeto.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projeto.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email); // Para buscar usuário por email
    Optional<Usuario> findByToken(String token);
    Optional<Usuario> findByNome(String nome);
    Optional<Usuario> findBySenha(String senha);
}
