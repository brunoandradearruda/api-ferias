// HistoricoFeriasDTO.java
package com.seplagpb.apiferiasseplagpb.dto;

import java.time.LocalDate;

public class HistoricoFeriasDTO {

    private Long funcionarioId;
    private String funcionarioNome;
    private LocalDate dataInicio;
    private LocalDate dataTermino;
    private int diasGozados;
    private int ano;
    

    public HistoricoFeriasDTO(Long funcionarioId, String funcionarioNome, LocalDate dataInicio, LocalDate dataTermino, int diasGozados, int ano) {
        this.funcionarioId = funcionarioId;
        this.funcionarioNome = funcionarioNome;
        this.dataInicio = dataInicio;
        this.dataTermino = dataTermino;
        this.diasGozados = diasGozados;
        this.ano = ano;
    }

    public Long getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public String getFuncionarioNome() {
        return funcionarioNome;
    }

    public void setFuncionarioNome(String funcionarioNome) {
        this.funcionarioNome = funcionarioNome;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataTermino() {
        return dataTermino;
    }

    public void setDataTermino(LocalDate dataTermino) {
        this.dataTermino = dataTermino;
    }

    public int getDiasGozados() {
        return diasGozados;
    }

    public void setDiasGozados(int diasGozados) {
        this.diasGozados = diasGozados;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
