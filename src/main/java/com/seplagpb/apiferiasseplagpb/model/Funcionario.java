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


    // Construtores, getters e setters
    public Funcionario() {}

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
    }

    public void adicionarDiasFeriasGozados(int dias) {
        this.diasFeriasGozados += dias; // Soma os novos dias aos já existentes
    }

}
