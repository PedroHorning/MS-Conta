package com.example.msaccount.model;

import java.time.LocalDate;

import com.example.msaccount.dto.AccountRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "conta")
@Entity(name = "conta")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conta")
    private Long id;

    @Column(name = "numero_conta")
    private String numeroConta;

    @Column(name = "data_criacao")
    private LocalDate dataCriacao;

    @Column(name = "limite")
    private Double limite;

    @Column(name = "id_cliente")
    private Long idCliente;

    @Column(name = "id_gerente")
    private Long idGerente;

    @Column(name = "saldo")
    private Double saldo;

    public Account() {
    }

    public Account(AccountRequestDTO data) {
        this.saldo = data.saldo();
        this.idCliente = data.idCliente();
        this.idGerente = data.idGerente();
        this.dataCriacao = LocalDate.now();
        this.limite = data.limite();
        this.numeroConta = data.numeroConta();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Double getLimite() {
        return limite;
    }

    public void setLimite(Double limite) {
        this.limite = limite;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public Long getIdGerente() {
        return idGerente;
    }

    public void setIdGerente(Long idGerente) {
        this.idGerente = idGerente;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
}
