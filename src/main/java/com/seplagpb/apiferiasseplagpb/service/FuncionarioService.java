package com.seplagpb.apiferiasseplagpb.service;

import com.seplagpb.apiferiasseplagpb.dto.FuncionarioFeriasDTO;
import com.seplagpb.apiferiasseplagpb.model.Funcionario;
import com.seplagpb.apiferiasseplagpb.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public Funcionario salvarFuncionario(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    public boolean temDireitoAFerias(Long funcionarioId) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        LocalDate dataAtual = LocalDate.now();
        long mesesTrabalhados = ChronoUnit.MONTHS.between(funcionario.getDataAdmissao(), dataAtual);
        return mesesTrabalhados >= 12;
    }

    public Funcionario registrarFeriasGozadas(Long funcionarioId, int diasGozados) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com ID: " + funcionarioId));

        int diasDeFeriasEmAberto = calcularFeriasEmAberto(funcionario);

        if (diasGozados <= 0 || diasGozados > diasDeFeriasEmAberto) {
            throw new IllegalArgumentException("Número de dias de férias inválido ou superior ao saldo de dias em aberto.");
        }

        funcionario.adicionarDiasFeriasGozados(diasGozados);
        return funcionarioRepository.save(funcionario);
    }

    public void deletarFuncionario(Long funcionarioId) {
        if (!funcionarioRepository.existsById(funcionarioId)) {
            throw new EntityNotFoundException("Funcionário não encontrado com ID: " + funcionarioId);
        }
        funcionarioRepository.deleteById(funcionarioId);
    }

    public List<FuncionarioFeriasDTO> listarFuncionariosComFeriasPendentes() {
        return funcionarioRepository.findAll().stream()
                .filter(funcionario -> temDireitoAFerias(funcionario.getId()))
                .map(funcionario -> new FuncionarioFeriasDTO(funcionario.getId(), funcionario.getNome(), calcularFeriasEmAberto(funcionario)))
                .filter(dto -> dto.getDiasFeriasPendentes() > 0)
                .collect(Collectors.toList());
    }

    private int calcularFeriasEmAberto(Funcionario funcionario) {
        LocalDate hoje = LocalDate.now();
        long mesesTrabalhados = ChronoUnit.MONTHS.between(funcionario.getDataAdmissao(), hoje);
        int totalDiasFerias = (int) (mesesTrabalhados / 12) * 30;
        return totalDiasFerias - funcionario.getDiasFeriasGozados();
    }

    public Funcionario registrarPeriodoFerias(Long funcionarioId, LocalDate inicioFerias, int dias) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com ID: " + funcionarioId));

        int diasDeFeriasEmAberto = calcularFeriasEmAberto(funcionario);

        LocalDate dataMinimaParaFerias = funcionario.getDataAdmissao().plusMonths(12);
        if (inicioFerias.isBefore(dataMinimaParaFerias)) {
            throw new IllegalArgumentException("A data de início das férias é anterior ao período aquisitivo de 12 meses.");
        }

        if (dias > diasDeFeriasEmAberto) {
            throw new IllegalArgumentException("Não há dias de férias suficientes para o período solicitado.");
        }

        funcionario.adicionarDiasFeriasGozados(dias);
        return funcionarioRepository.save(funcionario);
    }
}
