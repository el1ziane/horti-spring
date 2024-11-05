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

import com.projeto.enums.Status;
import com.projeto.model.Faturamento;
import com.projeto.model.Producao;
import com.projeto.model.Recebimento;
import com.projeto.model.Venda;
import com.projeto.repository.ProducaoRepository;
import com.projeto.repository.RecebimentoRepository;
import com.projeto.repository.VendaRepository;

@Service
public class FaturamentoService {
    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private RecebimentoRepository recebimentoRepository;
    @Autowired
    private ProducaoRepository producaoRepository;

  
    public List<Faturamento> gerarFinanceiroPorMes(String mesAno) {
        if (mesAno == null || !mesAno.matches("\\d{4}-\\d{2}")) {
            throw new IllegalArgumentException("Formato de mesAno inválido: " + mesAno);
        }
        System.out.println("Recebido mesAno: " + mesAno);
        try {
            LocalDate mes = LocalDate.parse(mesAno + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        // Obtenha todas as vendas, compras e produções dentro do mês selecionado
        List<Venda> vendas = vendaRepository.findByDataInicialBetween(mes.withDayOfMonth(1), mes.withDayOfMonth(mes.lengthOfMonth()));
        List<Recebimento> compras = recebimentoRepository.findByDataRecebimentoBetween(mes.withDayOfMonth(1), mes.withDayOfMonth(mes.lengthOfMonth()));
        List<Producao> producoes = producaoRepository.findByDataProducaoBetween(mes.withDayOfMonth(1), mes.withDayOfMonth(mes.lengthOfMonth()));
    
        Map<String, Faturamento> faturamentoMap = new HashMap<>();
    
        // Processar vendas
        for (Venda venda : vendas) {
            String produtoNome = venda.getProdutoVenda().getProdutoNome(); // Certifique-se de que retorna um objeto correto
            faturamentoMap.putIfAbsent(produtoNome, new Faturamento());
            
            Faturamento faturamento = faturamentoMap.get(produtoNome);
            faturamento.setProdutoFat(produtoNome);
            Integer quantidadeAtual = faturamento.getVendaFat(); // Mude para Integer
            if (quantidadeAtual == null) {
                quantidadeAtual = 0; // Se for null, inicie com 0
            }
            faturamento.setVendaFat(quantidadeAtual + venda.getQuantidadeVenda());

         
    //  // Debug: Log o valor da venda atual
    System.out.println("Produto: " + produtoNome + ", Valor Venda: " + venda.getValorVenda());
    
    // Acesse e some o valor total
    double valorAtual = faturamento.getValorFat() == null ? 0.0 : faturamento.getValorFat(); // Corrigido para Double
    faturamento.setValorFat(valorAtual + venda.getValorVenda());
    
    // Debug: Log após a soma do valor
    System.out.println("Depois da soma do valor - Produto: " + produtoNome + ", Novo Valor Fat: " + faturamento.getValorFat());}
    
        // Processar produções
        for (Producao producao : producoes) {
            String produtoNome = producao.getProdutoProducao().getProdutoNome(); // Certifique-se de que retorna um objeto correto
            faturamentoMap.putIfAbsent(produtoNome, new Faturamento());
            
            Faturamento faturamento = faturamentoMap.get(produtoNome);
            //faturamento.setProdFat(producao.getQuantidadeProducao());
            Integer quantidadeAtualProd = faturamento.getProdFat(); // Mude para Integer
            if (quantidadeAtualProd == null) {
                quantidadeAtualProd = 0; // Se for null, inicie com 0
            }
            faturamento.setProdFat(quantidadeAtualProd + producao.getQuantidadeProducao());
          
    // Verifique se vendaFat está corretamente inicializado, caso contrário, inicie com 0
    
    
    // Calcule a perca somente após garantir que prodFat e vendaFat estão definidos
    Integer perda = faturamento.getProdFat() - faturamento.getVendaFat();
    
    // Se perca for negativa, defina como zero
    perda = Math.max(0, perda);
    
    // Acumule a perca anterior, se houver
    faturamento.setPerdaFat((faturamento.getPerdaFat() == null ? 0 : faturamento.getPerdaFat()) + perda);
        
        }
    
        // Processar compras
        for (Recebimento recebimento : compras) {
            String produtoNome = recebimento.getProduto().getProdutoNome(); // Certifique-se de que retorna um objeto correto
            faturamentoMap.putIfAbsent(produtoNome, new Faturamento());
            
            Faturamento faturamento = faturamentoMap.get(produtoNome);                   
    //  // Debug: Log o valor da venda atual
    System.out.println("Produto: " + produtoNome + ", Valor Venda: " + recebimento.getValorRecebimento());

    // Acesse e some o valor total
    double valorCompraAtual = faturamento.getVcompraFat() == null ? 0.0 : faturamento.getVcompraFat(); // Corrigido para Double
    faturamento.setVcompraFat(valorCompraAtual + recebimento.getValorRecebimento());
    
            faturamento.setLucroFat(faturamento.getValorFat() - faturamento.getVcompraFat());
        }
    
        return new ArrayList<>(faturamentoMap.values());
         } catch (DateTimeParseException e) {
        throw new IllegalArgumentException("Formato de mesAno inválido: " + mesAno);
    }
    }
    
   
    
}

