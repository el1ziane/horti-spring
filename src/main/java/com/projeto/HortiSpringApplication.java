package com.projeto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.projeto.service.AuthService;

@SpringBootApplication
public class HortiSpringApplication  {
//implements CommandLineRunner
      @Autowired
    private AuthService authService;

    public static void main(String[] args) {
        SpringApplication.run(HortiSpringApplication.class, args);
    }
   /*/ @Override
    public void run(String... args) throws Exception {
        // Chama o método para criar o usuário padrão
        authService.criarUsuarioPadrao();
    }*/
}
