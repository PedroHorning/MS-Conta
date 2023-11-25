package com.example.msaccount.model;

import java.time.LocalDateTime;

import com.example.msaccount.dto.TransactionRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "movimentacao")
@Entity(name = "movimentacao")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimentacao")
    private Long id;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "id_conta_origem")
    private Long idContaOrigem;

    @Column(name = "id_conta_destino")
    private Long idContaDestino;

    @Column(name = "id_cliente_origem")
    private Long idClienteOrigem;

    @Column(name = "id_cliente_destino")
    private Long idClienteDestino;

    public Transaction() {
    }

    public Transaction(TransactionRequestDTO data) {
        this.dataHora = LocalDateTime.now();
        this.valor = data.valor();
        this.tipo = data.tipo();
        this.idClienteDestino = data.idClinteDestino();
        this.idClienteOrigem = data.idClienteOrigem();
        this.idContaDestino = data.idContaDestino();
        this.idContaOrigem = data.idContaOrigem();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Long getIdContaOrigem() {
        return idContaOrigem;
    }

    public void setIdContaOrigem(Long idContaOrigem) {
        this.idContaOrigem = idContaOrigem;
    }

    public Long getIdContaDestino() {
        return idContaDestino;
    }

    public void setIdContaDestino(Long idContaDestino) {
        this.idContaDestino = idContaDestino;
    }

    public Long getIdClienteOrigem() {
        return idClienteOrigem;
    }

    public void setIdClienteOrigem(Long idClienteOrigem) {
        this.idClienteOrigem = idClienteOrigem;
    }

    public Long getIdClienteDestino() {
        return idClienteDestino;
    }

    public void setIdClienteDestino(Long idClienteDestino) {
        this.idClienteDestino = idClienteDestino;
    }
}
