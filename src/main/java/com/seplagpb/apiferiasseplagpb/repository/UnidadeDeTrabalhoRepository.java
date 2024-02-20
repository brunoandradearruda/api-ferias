package com.seplagpb.apiferiasseplagpb.repository;
import com.seplagpb.apiferiasseplagpb.model.UnidadeDeTrabalho;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeDeTrabalhoRepository extends JpaRepository<UnidadeDeTrabalho, Long> {
}
