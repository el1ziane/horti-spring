package com.projeto.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.projeto.model.Usuario;
import com.projeto.repository.UsuarioRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import jakarta.annotation.PostConstruct;

@Service
public class AuthService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    private static final String SECRET_KEY = "chave-secreta";
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String gerarToken(Usuario usuario) {
        Date dataExpiracao = new Date(System.currentTimeMillis() + 3600000);

        String token = Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        // System.out.println("Token gerado: " + token);

        return token;
    }

    public boolean validarToken(String token) {
        // System.out.println("Token recebido para validação: " + token);
        try {
            Date expirationDate = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();

            long remainingTime = expirationDate.getTime() - System.currentTimeMillis();
            long secondsRemaining = remainingTime / 1000;

            if (remainingTime > 0) {
                System.out.println("Token válido. Tempo restante até expirar: " + secondsRemaining + " segundos");
                return true;
            } else {
                System.out.println("Token expirado.");
            }
        } catch (SignatureException e) {
            System.out.println("Token inválido: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro ao validar token: " + e.getMessage());
        }
        return false;
    }

    public boolean isTokenExpired(String token) {
        try {
            Date expirationDate = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
            return expirationDate.before(new Date());
        } catch (Exception e) {
            System.out.println("Erro ao verificar a expiração do token: " + e.getMessage());
            return true;
        }
    }
 
    /*  @PostConstruct
      public void criarUsuarioPadrao() {
     if (usuarioRepository.count() == 0) {
      String senhaPadrao = "senha123";
      String senhaCodificada = passwordEncoder.encode(senhaPadrao);
     // System.out.println("Senha codificada: " + senhaCodificada); 
      Usuario usuario = new Usuario();
      usuario.setEmail("eliziane@edu.unifil.br");
      usuario.setNome("Usuário Padrão");
      usuario.setSenha(senhaCodificada);
      usuarioRepository.save(usuario);
      
     System.out.println("Usuário salvo com sucesso: " + usuario.getEmail());
      }
    }*/
}