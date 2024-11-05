package com.projeto.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projeto.dto.FornecedorDTO;
import com.projeto.dto.UsuarioDTO;
import com.projeto.model.Usuario;
import com.projeto.service.AuthService;
import com.projeto.service.UsuarioService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class UsuarioController {

    
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AuthService authService;
    @GetMapping("/{id}")
public UsuarioDTO findById(@PathVariable @NotNull @Positive Long id) {
    System.out.println("ID recebido: " + id);
    return usuarioService.findById(id);
}

    
    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Usuario usuario) {
    ResponseEntity<?> response = usuarioService.login(usuario);

    if (response.getStatusCode() == HttpStatus.OK) {
        Map<String, String> responseBody = (Map<String, String>) response.getBody();
        String token = responseBody.get("token");

        // Aqui chamamos o método de validação do token
        boolean tokenValido = authService.validarToken(token);
        if (tokenValido) {
            return ResponseEntity.ok(Map.of("message", "Login bem-sucedido", "token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
    } else {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }
}
@GetMapping("/me") 
public ResponseEntity<UsuarioDTO> getUsuarioLogado() {
    UsuarioDTO usuarioDTO = usuarioService.getUsuarioLogado();
    return ResponseEntity.ok(usuarioDTO);
}

 
  @PutMapping("/{id}")
    public UsuarioDTO atualizarUsuario(@PathVariable @NotNull @Positive Long id, @RequestBody @Valid UsuarioDTO usuario){
        return usuarioService.atualizarUsuario(id, usuario);
    }
    @PostMapping("/recuperar-senha")
    public ResponseEntity<Map<String, String>> enviarEmail(@RequestBody UsuarioDTO usuarioDTO) {
        boolean isEmailSent = usuarioService.enviaEmail(usuarioDTO);
        
        if (isEmailSent) {
            return ResponseEntity.status(HttpStatus.CREATED).body(Collections.singletonMap("message", "Email enviado com sucesso."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Usuário não encontrado."));
        }
    }
    
    
    @PutMapping("/alterar-senha")
    public ResponseEntity<?> alterarSenha(@RequestBody Map<String, String> body) {
        String senhaAtual = body.get("senhaAtual");
        String novaSenha = body.get("novaSenha");
        
        Long usuarioId = 1L;
    
        boolean sucesso = usuarioService.alterarSenha(usuarioId, senhaAtual, novaSenha);
        
        if (sucesso) {
            return ResponseEntity.ok(Map.of("message", "Senha alterada com sucesso."));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Senha atual incorreta ou usuário não encontrado."));
        }
    }
    

    @PostMapping("/redefinir-senha")
    public ResponseEntity<?> redefinirSenha(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String novaSenha = body.get("novaSenha");
    
        
        boolean sucesso = usuarioService.redefinirSenha(token, novaSenha);
    
        if (sucesso) {
            return ResponseEntity.ok(Map.of("message", "Senha redefinida com sucesso.")); 
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Token inválido ou expirado.")); 
        }
    }
    
    
}
