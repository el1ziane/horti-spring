package com.projeto.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.projeto.dto.FornecedorDTO;
import com.projeto.dto.UsuarioDTO;
import com.projeto.dto.mapper.UsuarioMapper;
import com.projeto.exception.RecordNotFoundException;
import com.projeto.model.Usuario;
import com.projeto.repository.UsuarioRepository;

import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
public class UsuarioService {

   
    private final UsuarioMapper usuarioMapper;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private AuthService authService; // Injetando AuthService

    public UsuarioService(UsuarioMapper usuarioMapper) {
        this.usuarioMapper = usuarioMapper;
    }
    
     public UsuarioDTO findById(@NotNull @Positive Long id){
        return usuarioRepository.findById(id).map(usuarioMapper::toDTO)
        .orElseThrow(()-> new RecordNotFoundException(id));
    }
    public UsuarioDTO getUsuarioLogado() {
        // Assumindo que você já tem um usuário padrão que pode ser recuperado sem necessidade de autenticação.
        Long usuarioId = 1L; // ID do único usuário no sistema.
        return findById(usuarioId); // Método que busca o usuário pelo ID.
    }
    public boolean alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null || !passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            // Retorna false se o usuário não for encontrado ou a senha não corresponder
            return false;
        }
    
        // Se a senha atual estiver correta, altere a senha
        String senhaHashed = passwordEncoder.encode(novaSenha); // Codifica a nova senha
        usuario.setSenha(senhaHashed);
        usuarioRepository.save(usuario);
        return true;
    }
    
    
    public ResponseEntity<?> login(Usuario usuario) {
        System.out.println("Tentando logar usuário: " + usuario.getEmail());
        Optional<Usuario> usuarioExistenteOpt = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioExistenteOpt.isPresent()) {
            Usuario usuarioExistente = usuarioExistenteOpt.get();
            System.out.println("Usuário encontrado: " + usuarioExistente.getEmail());

            if (passwordEncoder.matches(usuario.getSenha(), usuarioExistente.getSenha())) {
                // Gere um token aqui
                String token = authService.gerarToken(usuarioExistente); // Usando AuthService
                return ResponseEntity.ok(Map.of("token", token));
            } else {
                System.out.println("Senhas não coincidem.");
            }
        } else {
            System.out.println("Usuário não encontrado.");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
    }
    


    public boolean redefinirSenha(String token, String novaSenha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByToken(token);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            String senhaHashed = passwordEncoder.encode(novaSenha); // Hashing da nova senha
            usuario.setSenha(senhaHashed);
            usuario.setToken(null); // Limpa o token após redefinir a senha
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }
    
 
     public UsuarioDTO atualizarUsuario(@NotNull @Positive Long id, @Valid UsuarioDTO usuario){
        return usuarioRepository.findById(id).map(registroEncontrado ->{
            registroEncontrado.setNome(usuario.nome());
            registroEncontrado.setEmail(usuario.email());
            return usuarioMapper.toDTO(usuarioRepository.save(registroEncontrado));
        }).orElseThrow(()-> new RecordNotFoundException(id));
     }
    public boolean enviaEmail(UsuarioDTO usuarioDTO) {
        try {
            Usuario usuarioParaBuscar = usuarioMapper.toEntity(usuarioDTO);
            Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuarioParaBuscar.getEmail());

            if (usuarioExistente.isPresent()) {
                Usuario usuario = usuarioExistente.get();
                String token = authService.gerarToken(usuario); 
                String linkRecuperacao = "http://localhost:4200/redefinir-senha?token=" + token;

               // String token = generateToken(); // Gera o token único
                usuario.setToken(token);
                System.out.println("Token gerado: " + token); // Adicione este log
                
                usuarioRepository.save(usuario);
                System.out.println("Token salvo para o usuário: " + usuario.getEmail()); // Verifica se o usuário foi salvo com o token
                Optional<Usuario> usuarioSalvo = usuarioRepository.findById(usuario.getId());
System.out.println("Token salvo no banco: " + usuarioSalvo.get().getToken());

                // Configura o email
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                
                helper.setFrom("noreply@gmail.com");
                helper.setSubject("Redefinir Senha");
                helper.setTo(usuario.getEmail());

                // Carrega o template de email
                String template = carregaTemplateEmail();
                template = template.replace("#{linkRecuperacao}", linkRecuperacao);
                template = template.replace("#{nome}", usuario.getNome() != null ? usuario.getNome() : "Usuário");

                helper.setText(template, true);
                javaMailSender.send(message); // Envia o email

                System.out.println("Email enviado com sucesso para " + usuario.getEmail());
                return true; // Retorna verdadeiro se o email foi enviado
            } else {
                System.out.println("Usuário com email " + usuarioParaBuscar.getEmail() + " não encontrado.");
                return false; // Retorna falso se o usuário não foi encontrado
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Falha ao enviar o email: " + exception.getMessage());
            return false; // Retorna falso em caso de erro
        }
    }

    public String carregaTemplateEmail() throws IOException {
        ClassPathResource resource = new ClassPathResource("template-email.html");
        String template = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        return template;
    }
}
