package com.seplagpb.apiferiasseplagpb.service;

import com.seplagpb.apiferiasseplagpb.dto.HistoricoFeriasDTO;
import com.seplagpb.apiferiasseplagpb.model.Funcionario;
import com.seplagpb.apiferiasseplagpb.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoricoFeriasService {

    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public HistoricoFeriasService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<HistoricoFeriasDTO> gerarRelatorioHistoricoFeriasGozadas() {
        return funcionarioRepository.findAll().stream()
                .filter(funcionario -> funcionario.getInicioFerias() != null && funcionario.getFimFerias() != null)
                .map(funcionario -> new HistoricoFeriasDTO(
                        funcionario.getId(),
                        funcionario.getNome(),
                        funcionario.getInicioFerias(),
                        funcionario.getFimFerias(),
                        funcionario.getDiasFeriasGozados(),
                        funcionario.getInicioFerias().getYear()
                ))
                .collect(Collectors.toList());
    }
}
