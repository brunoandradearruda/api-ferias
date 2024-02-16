package com.seplagpb.apiferiasseplagpb.dto;

import java.time.LocalDate;

public class RegistrarFeriasRequest {
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
}
