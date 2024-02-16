package com.seplagpb.apiferiasseplagpb.service;
import com.seplagpb.apiferiasseplagpb.model.Funcionario;
import com.seplagpb.apiferiasseplagpb.model.SolicitacaoFerias;
import com.seplagpb.apiferiasseplagpb.repository.FuncionarioRepository;
import com.seplagpb.apiferiasseplagpb.repository.SolicitacaoFeriasRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SolicitacaoFeriasService {

    private final SolicitacaoFeriasRepository solicitacaoFeriasRepository;
    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    public SolicitacaoFeriasService(SolicitacaoFeriasRepository solicitacaoFeriasRepository, FuncionarioRepository funcionarioRepository) {
        this.solicitacaoFeriasRepository = solicitacaoFeriasRepository;
        this.funcionarioRepository = funcionarioRepository;
    }

    public SolicitacaoFerias criarSolicitacaoFerias(Long funcionarioId, SolicitacaoFerias solicitacao) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
        solicitacao.setFuncionario(funcionario);
        return solicitacaoFeriasRepository.save(solicitacao);
    }

    // Método para calcular dias de férias pendentes
    public int calcularDiasFeriasPendentes(Long funcionarioId) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado"));
        LocalDate hoje = LocalDate.now();
        long mesesTrabalhados = ChronoUnit.MONTHS.between(funcionario.getDataAdmissao(), hoje);
        int diasFerias = (int) (mesesTrabalhados / 12) * 30; // 30 dias para cada ano de serviço
        List<SolicitacaoFerias> solicitacoes = solicitacaoFeriasRepository.findByFuncionarioId(funcionarioId);
        int diasSolicitados = solicitacoes.stream().mapToInt(SolicitacaoFerias::getQuantidadeDias).sum();
        return diasFerias - diasSolicitados;
    }
}
