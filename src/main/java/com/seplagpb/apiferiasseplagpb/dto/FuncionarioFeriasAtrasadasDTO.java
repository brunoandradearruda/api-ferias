package com.seplagpb.apiferiasseplagpb.dto;


public class FuncionarioFeriasAtrasadasDTO {

    private Long id;
    private String nome;
    private int diasFeriasAtrasadas;

    public FuncionarioFeriasAtrasadasDTO(Long id, String nome, int diasFeriasAtrasadas) {
        this.id = id;
        this.nome = nome;
        this.diasFeriasAtrasadas = diasFeriasAtrasadas;
    }

    // Getters e setters
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

    public int getDiasFeriasAtrasadas() {
        return diasFeriasAtrasadas;
    }

    public void setDiasFeriasAtrasadas(int diasFeriasAtrasadas) {
        this.diasFeriasAtrasadas = diasFeriasAtrasadas;
    }
}
