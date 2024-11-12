package com.projeto.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.model.Compra;
import com.projeto.model.Faturamento;
import com.projeto.model.Producao;

import com.projeto.model.Venda;
import com.projeto.repository.CompraRepository;
import com.projeto.repository.ProducaoRepository;

import com.projeto.repository.VendaRepository;

@Service
public class FaturamentoService {
    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private ProducaoRepository producaoRepository;

    public List<Faturamento> gerarFinanceiroPorMes(String mesAno) {
        if (mesAno == null || !mesAno.matches("\\d{4}-\\d{2}")) {
            throw new IllegalArgumentException("Formato de mesAno inválido: " + mesAno);
        }
        System.out.println("Recebido mesAno: " + mesAno);
        try {
            LocalDate mes = LocalDate.parse(mesAno + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            List<Venda> vendas = vendaRepository.findByDataInicialBetween(mes.withDayOfMonth(1),
                    mes.withDayOfMonth(mes.lengthOfMonth()));
            List<Compra> compras = compraRepository.findByDataCompraBetween(mes.withDayOfMonth(1),
                    mes.withDayOfMonth(mes.lengthOfMonth()));
            List<Producao> producoes = producaoRepository.findByDataProducaoBetween(mes.withDayOfMonth(1),
                    mes.withDayOfMonth(mes.lengthOfMonth()));

            Map<String, Faturamento> faturamentoMap = new HashMap<>();

            for (Venda venda : vendas) {
                String produtoNome = venda.getProdutoVenda().getProdutoNome();
                faturamentoMap.putIfAbsent(produtoNome, new Faturamento());

                Faturamento faturamento = faturamentoMap.get(produtoNome);
                faturamento.setProdutoFat(produtoNome);
                Integer quantidadeAtual = faturamento.getVendaFat();
                if (quantidadeAtual == null) {
                    quantidadeAtual = 0;
                }
                faturamento.setVendaFat(quantidadeAtual + venda.getQuantidadeVenda());

                System.out.println("Produto: " + produtoNome + ", Valor Venda: " + venda.getValorVenda());

                double valorAtual = faturamento.getValorFat() == null ? 0.0 : faturamento.getValorFat();
                faturamento.setValorFat(valorAtual + venda.getValorVenda());

                System.out.println("Depois da soma do valor - Produto: " + produtoNome + ", Novo Valor Fat: "
                        + faturamento.getValorFat());
            }

            for (Producao producao : producoes) {
                String produtoNome = producao.getProdutoProducao().getProdutoNome();
                faturamentoMap.putIfAbsent(produtoNome, new Faturamento());

                Faturamento faturamento = faturamentoMap.get(produtoNome);
                // faturamento.setProdFat(producao.getQuantidadeProducao());
                Integer quantidadeAtualProd = faturamento.getProdFat();
                if (quantidadeAtualProd == null) {
                    quantidadeAtualProd = 0;
                }
                faturamento.setProdFat(quantidadeAtualProd + producao.getQuantidadeProducao());

                Integer perda = faturamento.getProdFat() - faturamento.getVendaFat();

                perda = Math.max(0, perda);

                faturamento.setPerdaFat((faturamento.getPerdaFat() == null ? 0 : faturamento.getPerdaFat()) + perda);

            }

            for (Compra recebimento : compras) {
                String produtoNome = recebimento.getProdutoCompra().getProdutoNome();
                faturamentoMap.putIfAbsent(produtoNome, new Faturamento());

                Faturamento faturamento = faturamentoMap.get(produtoNome);

                double valorCompraAtual = faturamento.getVcompraFat() == null ? 0.0 : faturamento.getVcompraFat();
                faturamento.setVcompraFat(valorCompraAtual + recebimento.getValorCompra());

                faturamento.setLucroFat(faturamento.getValorFat() - faturamento.getVcompraFat());
            }

            return new ArrayList<>(faturamentoMap.values());
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de mesAno inválido: " + mesAno);
        }
    }

}