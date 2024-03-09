package com.seplagpb.apiferiasseplagpb.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Departamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String unidadeTrabalho;
    private String nomeDepartamento;

    public static final String SEPLAG = "SEPLAG";

    public Departamento() {
    }

    public Departamento(String nomeDepartamento) {
        this.nomeDepartamento = nomeDepartamento;
    }

    public Departamento(Long id, String unidadeTrabalho, String nomeDepartamento) {
        this.id = id;
        this.unidadeTrabalho = unidadeTrabalho;
        this.nomeDepartamento = nomeDepartamento;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUnidadeTrabalho() {
        return unidadeTrabalho;
    }

    public void setUnidadeTrabalho(String unidadeTrabalho) {
        this.unidadeTrabalho = unidadeTrabalho;
    }

    public String getNomeDepartamento() {
        return nomeDepartamento;
    }

    public void setNomeDepartamento(String nomeDepartamento) {
        this.nomeDepartamento = nomeDepartamento;
    }
}
