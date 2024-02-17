package com.seplagpb.apiferiasseplagpb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate dataAdmissao;
    private Integer diasFeriasGozados = 0; // Inicializado com 0
    private LocalDate inicioFerias;
    private LocalDate fimFerias;
    private Integer diasFeriasRestantes = 30; // Inicializado com o total de dias de férias por ano


    public Funcionario() {}



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(LocalDate dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public Integer getDiasFeriasGozados() {
        return diasFeriasGozados;
    }

    public void setDiasFeriasGozados(Integer diasFeriasGozados) {
        this.diasFeriasGozados = diasFeriasGozados;
        // Atualiza os dias de férias restantes com base nos dias de férias gozados
        this.diasFeriasRestantes = 30 - diasFeriasGozados;
    }


    public LocalDate getInicioFerias() {
        return inicioFerias;
    }

    public void setInicioFerias(LocalDate inicioFerias) {
        this.inicioFerias = inicioFerias;
    }

    public LocalDate getFimFerias() {
        return fimFerias;
    }

    public void setFimFerias(LocalDate fimFerias) {
        this.fimFerias = fimFerias;
    }

    public void adicionarDiasFeriasGozados(int dias) {
        if (dias < 0) {
            throw new IllegalArgumentException("Não é possível adicionar uma quantidade negativa de dias de férias.");
        }

        int novosDiasFeriasGozados = this.diasFeriasGozados + dias;
        if (novosDiasFeriasGozados > 30) {
            throw new IllegalArgumentException("A quantidade total de dias de férias gozados excede o limite permitido de 30 dias.");
        }

        this.diasFeriasGozados = novosDiasFeriasGozados;
    }

    public Integer getDiasFeriasRestantes() {
        return diasFeriasRestantes;
    }

    public void setDiasFeriasRestantes(Integer diasFeriasRestantes) {
        this.diasFeriasRestantes = diasFeriasRestantes;
    }

}
