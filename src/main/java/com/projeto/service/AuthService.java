package com.projeto.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.projeto.model.Usuario;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
@Service
public class AuthService {
private static final String SECRET_KEY = "sua-chave-secreta"; // use uma chave forte e mantenha-a segura

    public String gerarToken(Usuario usuario) {
        Date dataExpiracao = new Date(System.currentTimeMillis() + 3600000);

    
        String token = Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    
        System.out.println("Token gerado: " + token); // Log do token gerado
        System.out.println("Token gerado: " + token); // Log do token gerado

        return token;
    }
    
    
    
    public boolean validarToken(String token) {
        System.out.println("Token recebido para validação: " + token); // Log do token recebido
        try {
            Date expirationDate = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration();
    
            // Calcula o tempo restante até a expiração
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
        return false; // Retorna false se o token estiver expirado ou inválido
    }
    
    
    public boolean isTokenExpired(String token) {
        try {
            Date expirationDate = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
            return expirationDate.before(new Date()); // Retorna true se o token estiver expirado
        } catch (Exception e) {
            System.out.println("Erro ao verificar a expiração do token: " + e.getMessage());
            return true; // Considera o token expirado em caso de erro
        }
    }
    
}
