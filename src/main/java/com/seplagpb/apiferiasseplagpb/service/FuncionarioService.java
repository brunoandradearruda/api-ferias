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

        if (diasGozados <= 0 || diasGozados > 30) {
            throw new IllegalArgumentException("Número de dias de férias inválido.");
        }

        funcionario.setDiasFeriasGozados(funcionario.getDiasFeriasGozados() + diasGozados);
        funcionarioRepository.save(funcionario);

        return funcionario;
    }


    public void deletarFuncionario(Long funcionarioId) {
        if (!funcionarioRepository.existsById(funcionarioId)) {
            throw new EntityNotFoundException("Funcionário não encontrado com ID: " + funcionarioId);
        }
        funcionarioRepository.deleteById(funcionarioId);
    }

    public List<FuncionarioFeriasDTO> listarFuncionariosComFeriasPendentes() {
        LocalDate hoje = LocalDate.now();
        return funcionarioRepository.findAll().stream()
                .filter(funcionario -> ChronoUnit.MONTHS.between(funcionario.getDataAdmissao(), hoje) >= 12) // Filtra por período aquisitivo
                .map(funcionario -> {
                    int diasFeriasPendentes = 30 - funcionario.getDiasFeriasGozados(); // Calcula dias de férias pendentes
                    if (diasFeriasPendentes > 0) {
                        return new FuncionarioFeriasDTO(funcionario.getId(), funcionario.getNome(), diasFeriasPendentes);
                    }
                    return null;
                })
                .filter(obj -> obj != null) // Remove funcionários sem férias pendentes
                .collect(Collectors.toList());
    }

    private int calcularDiasFeriasPendentes(Funcionario funcionario) {
        // Implemente sua lógica para calcular os dias de férias pendentes
        // Por exemplo, retornar 30 menos os dias de férias já gozados
        return 30 - funcionario.getDiasFeriasGozados();
    }

    public Funcionario registrarPeriodoFerias(Long funcionarioId, LocalDate inicioFerias, int dias) throws Exception {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com ID: " + funcionarioId));

        LocalDate hoje = LocalDate.now();
        long mesesDeServico = ChronoUnit.MONTHS.between(funcionario.getDataAdmissao(), hoje);

        if (mesesDeServico < 12) {
            throw new IllegalStateException("Funcionário com menos de 12 meses de serviço não pode gozar férias.");
        }

        // Verifica se a quantidade de dias é válida
        if (dias <= 0 || dias > 30) {
            throw new IllegalArgumentException("Quantidade de dias de férias inválida.");
        }

        // Aqui você adiciona a lógica para verificar se o funcionário tem dias de férias pendentes suficientes, se necessário

        // E aqui, você registra o período de férias no banco de dados, conforme necessário

        return funcionario;
    }







}

