package com.projeto;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.projeto.enums.Status;
import com.projeto.model.Recebimento;
import com.projeto.repository.RecebimentoRepository;

@SpringBootApplication
public class HortiSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(HortiSpringApplication.class, args);
    }
	@Bean
    CommandLineRunner initDatabase(RecebimentoRepository recebimentoRepository) {
        return args -> {
            recebimentoRepository.deleteAll();

            Recebimento r = new Recebimento();
            r.setFornecedor("Casa das Etiquetas");
            r.setProduto("Rolo de Etiquetas");
            r.setQuantidadeRecebimento(10);
            r.setValorRecebimento(300);
            r.setDataRecebimento("21-04-2024");
            r.setStatus(Status.PAGO);

            recebimentoRepository.save(r);
        };
    }

}
