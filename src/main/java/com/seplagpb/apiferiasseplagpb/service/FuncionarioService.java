package com.seplagpb.apiferiasseplagpb.service;

import com.seplagpb.apiferiasseplagpb.dto.FuncionarioFeriasAtrasadasDTO;
import com.seplagpb.apiferiasseplagpb.dto.FuncionarioFeriasDTO;
import com.seplagpb.apiferiasseplagpb.dto.RegistrarFeriasRequest;
import com.seplagpb.apiferiasseplagpb.model.Funcionario;
import com.seplagpb.apiferiasseplagpb.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;


    @Autowired
    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    public boolean verificaFeriasRegistradas(Long funcionarioId, LocalDate inicioFerias, int dias) {
        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com ID: " + funcionarioId));
        LocalDate fimFerias = inicioFerias.plusDays(dias - 1);


        if (funcionario.getInicioFerias() != null && !fimFerias.isBefore(funcionario.getInicioFerias()) &&
                !inicioFerias.isAfter(funcionario.getFimFerias())) {
            return true;
        }
        return false;
    }


    public Funcionario salvarFuncionario(Funcionario funcionario) {

        funcionario.setDiasFeriasRestantes(null);


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


        if (funcionario.getInicioFerias() != null && !inicioFerias.isAfter(funcionario.getFimFerias())) {
            throw new IllegalArgumentException("Já existem férias registradas para o período solicitado.");
        }


        if (inicioFerias.isBefore(funcionario.getDataAdmissao())) {
            throw new IllegalArgumentException("A data de início das férias não pode ser anterior à data de admissão do funcionário.");
        }

        if (funcionario.getDataUltimasFerias() != null && inicioFerias.isBefore(funcionario.getDataUltimasFerias())) {
            throw new IllegalArgumentException("A data de início das férias não pode ser anterior à última data de férias gozadas.");
        }


        LocalDate dataMinimaParaFerias = funcionario.getDataAdmissao().plusMonths(12);
        if (inicioFerias.isBefore(dataMinimaParaFerias)) {
            throw new IllegalArgumentException("Funcionário não tem tempo de serviço suficiente para gozar férias.");
        }


        if (!podeRegistrarFerias(funcionario, inicioFerias, dias)) {
            throw new IllegalArgumentException("Não é possível registrar férias para o período solicitado.");
        }


        funcionario.setDiasFeriasGozados(funcionario.getDiasFeriasGozados() + dias);
        funcionario.setInicioFerias(inicioFerias);
        LocalDate fimFerias = inicioFerias.plusDays(dias - 1);
        funcionario.setFimFerias(fimFerias);
        funcionario.setDataUltimasFerias(fimFerias); // Atualiza a data das últimas férias gozadas

        return funcionarioRepository.save(funcionario);
    }


    private boolean podeRegistrarFerias(Funcionario funcionario, LocalDate inicioFerias, int dias) {
        LocalDate hoje = LocalDate.now();


        if (funcionario.getDataUltimasFerias() != null && inicioFerias.isBefore(funcionario.getDataUltimasFerias())) {
            throw new IllegalArgumentException("A data de início das férias não pode ser anterior à última data de férias gozadas.");
        }


        int diasFeriasRestantes = calcularDiasFeriasRestantes(funcionario, hoje);


        return dias > 0 && dias <= diasFeriasRestantes;
    }


    private int calcularDiasFeriasRestantes(Funcionario funcionario, LocalDate hoje) {
        int diasFeriasRestantes = 30 - funcionario.getDiasFeriasGozados();


        if (funcionario.getDataUltimasFerias() != null) {
            long mesesDesdeUltimasFerias = ChronoUnit.MONTHS.between(funcionario.getDataUltimasFerias(), hoje);
            if (mesesDesdeUltimasFerias >= 12) {

                funcionario.setDiasFeriasGozados(0);
                return 30;
            }
        }

        return diasFeriasRestantes;
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


    public List<Funcionario> listarComFeriasPendentes() {


        return funcionarioRepository.findAll().stream()
                .filter(funcionario -> funcionario.getDiasFeriasRestantes() > 0)
                .collect(Collectors.toList());
    }

    public Funcionario salvar(Funcionario funcionario) {

        return funcionarioRepository.save(funcionario);
    }

    public void excluir(Long id) {
        funcionarioRepository.deleteById(id);
    }



    public List<Funcionario> listarFuncionariosCadastrados() {
        return funcionarioRepository.findAll();
    }

    public long calcularDiasAdicionaisFerias(Funcionario funcionario) {
        LocalDate dataAtual = LocalDate.now();
        LocalDate dataAdmissao = funcionario.getDataAdmissao();


        long mesesDeDiferenca = ChronoUnit.MONTHS.between(dataAdmissao, dataAtual);


        if (mesesDeDiferenca > 0 && mesesDeDiferenca % 12 == 0) {
            return 30;
        }

        return 0;
    }

    public Funcionario atualizarFuncionario(Long id, Funcionario funcionarioAtualizado) {
        return funcionarioRepository.findById(id)
                .map(funcionarioExistente -> {

                    if (funcionarioAtualizado.getNome() != null) {
                        funcionarioExistente.setNome(funcionarioAtualizado.getNome());
                    }

                    if (funcionarioAtualizado.getCpf() != null) {
                        funcionarioExistente.setCpf(funcionarioAtualizado.getCpf());
                    }

                    if (funcionarioAtualizado.getSexo() != null) {
                        funcionarioExistente.setSexo(funcionarioAtualizado.getSexo());
                    }


                    return funcionarioRepository.save(funcionarioExistente);
                })
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com o ID: " + id));
    }

    public List<Funcionario> listarTodos() {
        return funcionarioRepository.findAll();
    }


    public Optional<Funcionario> findById(Long id) {

        return funcionarioRepository.findById(id);
    }

    public void excluirPorId(Long id) {
        // Verifica se o funcionário existe antes de tentar excluir
        if (funcionarioRepository.existsById(id)) {
            funcionarioRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Funcionário com ID " + id + " não encontrado.");
        }
    }


}









