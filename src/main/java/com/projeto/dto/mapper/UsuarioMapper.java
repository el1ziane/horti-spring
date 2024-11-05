package com.projeto.dto.mapper;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

import com.projeto.dto.UsuarioDTO;
import com.projeto.model.Usuario;
@Component
public class UsuarioMapper {
    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("Usuario não pode ser nulo");
        }
        return new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
    
    public Usuario toEntity(UsuarioDTO usuarioDTO) {
        if (usuarioDTO == null) {
            throw new IllegalArgumentException("UsuarioDTO não pode ser nulo");
        }
    
       Usuario usuario = new Usuario();
     
    
       usuario.setNome(usuarioDTO.nome());
     

       usuario.setEmail(usuarioDTO.email());
       return usuario;
    }
}



