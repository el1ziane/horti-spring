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
    private AuthService authService;

    public UsuarioService(UsuarioMapper usuarioMapper) {
        this.usuarioMapper = usuarioMapper;
    }

    public UsuarioDTO findById(@NotNull @Positive Long id) {
        return usuarioRepository.findById(id).map(usuarioMapper::toDTO)
                .orElseThrow(() -> new RecordNotFoundException(id));
    }

    public UsuarioDTO getUsuarioLogado() {

        Long usuarioId = 1L;
        return findById(usuarioId);
    }

    public boolean alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElse(null);
        if (usuario == null || !passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            return false;
        }

        String senhaHashed = passwordEncoder.encode(novaSenha);
        usuario.setSenha(senhaHashed);
        usuarioRepository.save(usuario);
        return true;
    }

    public ResponseEntity<?> login(Usuario usuario) {
        //System.out.println("Tentando logar usuário: " + usuario.getEmail());
        Optional<Usuario> usuarioExistenteOpt = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioExistenteOpt.isPresent()) {
            Usuario usuarioExistente = usuarioExistenteOpt.get();
          //  System.out.println("Usuário encontrado: " + usuarioExistente.getEmail());

            if (passwordEncoder.matches(usuario.getSenha(), usuarioExistente.getSenha())) {

                String token = authService.gerarToken(usuarioExistente);
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
            String senhaHashed = passwordEncoder.encode(novaSenha);
            usuario.setSenha(senhaHashed);
            usuario.setToken(null);
            usuarioRepository.save(usuario);
            return true;
        }
        return false;
    }

    public UsuarioDTO atualizarUsuario(@NotNull @Positive Long id, @Valid UsuarioDTO usuario) {
        return usuarioRepository.findById(id).map(registroEncontrado -> {
            registroEncontrado.setNome(usuario.nome());
            registroEncontrado.setEmail(usuario.email());
            return usuarioMapper.toDTO(usuarioRepository.save(registroEncontrado));
        }).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public boolean enviaEmail(UsuarioDTO usuarioDTO) {
        try {
            Usuario usuarioParaBuscar = usuarioMapper.toEntity(usuarioDTO);
            Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuarioParaBuscar.getEmail());

            if (usuarioExistente.isPresent()) {
                Usuario usuario = usuarioExistente.get();
                String token = authService.gerarToken(usuario);
                String linkRecuperacao = "http://localhost:4200/redefinir-senha?token=" + token;
                usuario.setToken(token);
                // System.out.println("Token gerado: " + token); // Adicione este log

                usuarioRepository.save(usuario);
                // System.out.println("Token salvo para o usuário: " + usuario.getEmail());
                Optional<Usuario> usuarioSalvo = usuarioRepository.findById(usuario.getId());
                // System.out.println("Token salvo no banco: " + usuarioSalvo.get().getToken());

                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);

                helper.setFrom("noreply@gmail.com");
                helper.setSubject("Redefinir Senha");
                helper.setTo(usuario.getEmail());

                String template = carregaTemplateEmail();
                template = template.replace("#{linkRecuperacao}", linkRecuperacao);
                template = template.replace("#{nome}", usuario.getNome() != null ? usuario.getNome() : "Usuário");

                helper.setText(template, true);
                javaMailSender.send(message);

                System.out.println("Email enviado com sucesso para " + usuario.getEmail());
                return true;
            } else {
                System.out.println("Usuário com email " + usuarioParaBuscar.getEmail() + " não encontrado.");
                return false;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            System.out.println("Falha ao enviar o email: " + exception.getMessage());
            return false;
        }
    }

    public String carregaTemplateEmail() throws IOException {
        ClassPathResource resource = new ClassPathResource("template-email.html");
        String template = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        return template;
    }
}
