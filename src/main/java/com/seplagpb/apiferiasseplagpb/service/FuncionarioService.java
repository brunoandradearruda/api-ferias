package com.seplagpb.apiferiasseplagpb.service;

import com.seplagpb.apiferiasseplagpb.dto.FuncionarioFeriasAtrasadasDTO;
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

    public List<Funcionario> funcionariosEmFerias() {
        LocalDate hoje = LocalDate.now();
        return funcionarioRepository.findAll().stream()
                .filter(funcionario -> funcionario.getInicioFerias() != null && funcionario.getFimFerias() != null)
                .filter(funcionario -> !hoje.isBefore(funcionario.getInicioFerias()) && !hoje.isAfter(funcionario.getFimFerias()))
                .collect(Collectors.toList());
    }

    public Funcionario registrarPeriodoFerias(Long funcionarioId, LocalDate inicioFerias, int dias) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com ID: " + funcionarioId));

        LocalDate dataMinimaParaFerias = funcionario.getDataAdmissao().plusMonths(12);
        if (inicioFerias.isBefore(dataMinimaParaFerias)) {
            throw new IllegalArgumentException("Funcionário não tem tempo de serviço suficiente para gozar férias.");
        }

        int diasFeriasRestantes = 30 - funcionario.getDiasFeriasGozados();
        if (dias > diasFeriasRestantes) {
            throw new IllegalArgumentException("Funcionário não tem dias de férias suficientes para o período solicitado.");
        }

        funcionario.setDiasFeriasGozados(funcionario.getDiasFeriasGozados() + dias);
        funcionario.setInicioFerias(inicioFerias);
        LocalDate fimFerias = inicioFerias.plusDays(dias - 1);
        funcionario.setFimFerias(fimFerias);

        return funcionarioRepository.save(funcionario);
    }

    public List<FuncionarioFeriasAtrasadasDTO> listarFuncionariosComFeriasAtrasadas() {
        return funcionarioRepository.findAll().stream()
                .filter(funcionario -> {
                    LocalDate dataReferencia = funcionario.getDataUltimasFerias() != null ? funcionario.getDataUltimasFerias() : funcionario.getDataAdmissao();
                    long diasAtrasados = ChronoUnit.DAYS.between(dataReferencia.plusYears(1), LocalDate.now());
                    return diasAtrasados > 0;
                })
                .map(funcionario -> {
                    LocalDate dataReferencia = funcionario.getDataUltimasFerias() != null ? funcionario.getDataUltimasFerias() : funcionario.getDataAdmissao();
                    long diasAtrasados = ChronoUnit.DAYS.between(dataReferencia.plusYears(1), LocalDate.now());
                    return new FuncionarioFeriasAtrasadasDTO(funcionario.getId(), funcionario.getNome(), (int) diasAtrasados);
                })
                .collect(Collectors.toList());
    }
}
