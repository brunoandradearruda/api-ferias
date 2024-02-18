package com.seplagpb.apiferiasseplagpb.dto;


import java.time.LocalDate;

public class FuncionarioDTO {

    private String nome;
    private LocalDate dataAdmissao;
    // Outros campos conforme necessário


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
}
