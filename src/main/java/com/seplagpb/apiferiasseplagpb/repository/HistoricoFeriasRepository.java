package com.seplagpb.apiferiasseplagpb.repository;
import com.seplagpb.apiferiasseplagpb.model.HistoricoFerias;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoFeriasRepository extends JpaRepository<HistoricoFerias, Long> {
    // Você pode adicionar consultas específicas, se necessário
}
