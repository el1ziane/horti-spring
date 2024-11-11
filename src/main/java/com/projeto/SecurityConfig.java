package com.projeto;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.projeto.service.AuthService;
import com.projeto.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:4200"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable();

        return http.build();
    }

    public class TokenFilter implements Filter {
        @Autowired
        private UsuarioService usuarioService;

        @Autowired
        private AuthService authService;

        public TokenFilter(UsuarioService usuarioService) {
            this.usuarioService = usuarioService;
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String token = httpRequest.getHeader("Authorization");
            // System.out.println("Token recebido: " + token);

            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            } else {
                // System.out.println("Token ausente ou formato incorreto");
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Token ausente ou formato incorreto.");
                return;
            }

            String requestURI = httpRequest.getRequestURI();
            // System.out.println("URI da requisição: " + requestURI);
            if (requestURI.equals("/api/auth/login")) {
                chain.doFilter(request, response);
                return;
            }

            if (token == null || authService.isTokenExpired(token)) {
                // System.out.println("Token expirado ou inválido.");
                ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Token expirado ou inválido.");
                return;
            }

            System.out.println("Token válido, prosseguindo com a requisição...");
            chain.doFilter(request, response);
        }
    }
}