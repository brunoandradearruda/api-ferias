package com.seplagpb.apiferiasseplagpb.dto;



public class FuncionarioFeriasDTO {
    private Long id;
    private String nome;
    private int diasFeriasPendentes;

    public FuncionarioFeriasDTO(Long id, String nome, int diasFeriasPendentes) {
        this.id = id;
        this.nome = nome;
        this.diasFeriasPendentes = diasFeriasPendentes;
    }

    public Long getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getDiasFeriasPendentes() {
        return diasFeriasPendentes;
    }

    public void setDiasFeriasPendentes(int diasFeriasPendentes) {
        this.diasFeriasPendentes = diasFeriasPendentes;
    }


}