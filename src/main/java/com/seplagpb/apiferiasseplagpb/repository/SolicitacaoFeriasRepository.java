package com.seplagpb.apiferiasseplagpb.repository;

import com.seplagpb.apiferiasseplagpb.model.SolicitacaoFerias;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitacaoFeriasRepository extends JpaRepository<SolicitacaoFerias, Long> {
    List<SolicitacaoFerias> findByFuncionarioId(Long funcionarioId);
}