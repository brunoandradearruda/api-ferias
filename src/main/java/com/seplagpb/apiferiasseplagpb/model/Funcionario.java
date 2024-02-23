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
    private String sexo;
    private String cpf;
    private String identidade;
    private String orgaoExpedidor;
    private LocalDate dataNascimento;
    private String pisPasep;
    private String endereco;
    private String bairro;
    private String numero;
    private String cep;
    private String complemento;
    private String cidade;
    private String estado;
    private String email;
    private String celular;
    private String matricula;
    private String cargo;
    private String simbolo;
    private String funcao;
    private String setor;
    private String unidadeTrabalho;
    private LocalDate dataAdmissao;
    private Integer diasFeriasGozados = 0; // Inicializado com 0
    private LocalDate inicioFerias;
    private LocalDate fimFerias;
    private Integer diasFeriasRestantes = 30; // Inicializado com o total de dias de férias por ano
    private LocalDate dataUltimasFerias;

    public Funcionario() {}

    public LocalDate getDataUltimasFerias() {
        return this.dataUltimasFerias;
    }

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

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getIdentidade() {
        return identidade;
    }

    public void setIdentidade(String identidade) {
        this.identidade = identidade;
    }

    public String getOrgaoExpedidor() {
        return orgaoExpedidor;
    }

    public void setOrgaoExpedidor(String orgaoExpedidor) {
        this.orgaoExpedidor = orgaoExpedidor;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getPisPasep() {
        return pisPasep;
    }

    public void setPisPasep(String pisPasep) {
        this.pisPasep = pisPasep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
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

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getUnidadeTrabalho() {
        return unidadeTrabalho;
    }

    public void setUnidadeTrabalho(String unidadeTrabalho) {
        this.unidadeTrabalho = unidadeTrabalho;
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

    public void setDataUltimasFerias(LocalDate dataUltimasFerias) {
        this.dataUltimasFerias = dataUltimasFerias;
    }
}
