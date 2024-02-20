package com.seplagpb.apiferiasseplagpb.dto;

public class UnidadeDeTrabalhoDTO {

    private String setor;
    private String cargo;
    private String simbolo;

    // Construtor padrão
    public UnidadeDeTrabalhoDTO() {
    }

    // Construtor com parâmetros
    public UnidadeDeTrabalhoDTO(String setor, String cargo, String simbolo) {
        this.setor = setor;
        this.cargo = cargo;
        this.simbolo = simbolo;
    }

    // Getters e setters
    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }
}
