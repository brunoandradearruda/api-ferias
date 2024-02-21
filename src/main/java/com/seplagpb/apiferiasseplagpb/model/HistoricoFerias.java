// HistoricoFerias.java
package com.seplagpb.apiferiasseplagpb.model;

import jakarta.persistence.*;



@Entity
@Table(name = "historico_ferias")
public class HistoricoFerias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "funcionario_id", nullable = false)
    private Funcionario funcionario;

    @Column(name = "dias_gozados")
    private int diasGozados;

    // Adicione outros campos necessários

    // Getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public int getDiasGozados() {
        return diasGozados;
    }

    public void setDiasGozados(int diasGozados) {
        this.diasGozados = diasGozados;
    }


}
