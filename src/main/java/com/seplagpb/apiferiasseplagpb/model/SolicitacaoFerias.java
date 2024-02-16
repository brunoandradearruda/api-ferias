package com.seplagpb.apiferiasseplagpb.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class SolicitacaoFerias {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate inicioFerias;
    private int quantidadeDias;
    private LocalDate dataSolicitacao = LocalDate.now();

    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    // Construtor, getters e setters
}
