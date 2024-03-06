package com.seplagpb.apiferiasseplagpb.dto;

import lombok.Data;

import java.time.LocalDate;

@Data

public class RegistrarFeriasRequest {
    private Long funcionarioId;
    private LocalDate inicioFerias;
    private int dias;

    // Getters e setters

    public LocalDate getInicioFerias() {
        return inicioFerias;
    }

    public void setInicioFerias(LocalDate inicioFerias) {
        this.inicioFerias = inicioFerias;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public Long getFuncionarioId() {
        return funcionarioId;


    }



}
