package com.projeto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.projeto.dto.VendaDTO;
import com.projeto.dto.mapper.VendaMapper;
import com.projeto.exception.RecordNotFoundException;
import com.projeto.model.Venda;
import com.projeto.repository.VendaRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Service
@Validated
@SuppressWarnings("null")
public class VendaService {

    private final VendaRepository vendaRepository; 
    private final VendaMapper vendaMapper;

    public VendaService(VendaRepository vendaRepository, VendaMapper vendaMapper){
        this.vendaRepository = vendaRepository;
        this.vendaMapper = vendaMapper;
    }
    public List<VendaDTO> lista() {
        return vendaRepository.findAll()
        .stream()
        .map(vendaMapper::toDTO)
        .collect(Collectors.toList());
    }
    public VendaDTO findById(@NotNull @Positive Long id){
        return vendaRepository.findById(id).map(vendaMapper::toDTO)
        .orElseThrow(()-> new RecordNotFoundException(id));
    }
     public Venda adicionar( @Valid @NotNull VendaDTO venda){
       Venda v = new Venda();
       v.setCodigoVenda(venda.codigoVenda());
       v.setProdutoVenda(venda.produtoVenda());
       v.setQuantidadeVenda(venda.quantidadeVenda());
       v.setValorVenda(venda.valorVenda());
       v.setDataInicial(venda.dataInicial());
       v.setDataFinal(venda.dataFinal());
       v.setStatusVenda(vendaMapper.convertStatusValue(venda.statusVenda()));
       return vendaRepository.save(v);
     }
     public VendaDTO editar(@NotNull @Positive Long id, @Valid VendaDTO venda){
        return vendaRepository.findById(id).map(registroEncontrado ->{
            registroEncontrado.setCodigoVenda(venda.codigoVenda());
            registroEncontrado.setProdutoVenda(venda.produtoVenda());
            registroEncontrado.setQuantidadeVenda(venda.quantidadeVenda());
            registroEncontrado.setValorVenda(venda.valorVenda());
         //   registroEncontrado.setPeriodoVenda(venda.periodoVenda());
            registroEncontrado.setDataInicial(venda.dataInicial());
            registroEncontrado.setDataFinal(venda.dataFinal());
            registroEncontrado.setStatusVenda(vendaMapper.convertStatusValue(venda.statusVenda()));
            return vendaMapper.toDTO(vendaRepository.save(registroEncontrado));
        }).orElseThrow(()-> new RecordNotFoundException(id));

    }
    public void excluir(@NotNull @Positive Long id){

        vendaRepository.delete(vendaRepository.findById(id).orElseThrow(()-> new RecordNotFoundException(id)));

    }

}
