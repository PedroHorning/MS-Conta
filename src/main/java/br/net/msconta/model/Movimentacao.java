package br.net.msconta.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "movimentacoes")
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimentacao")
    private Integer id;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "id_conta_origem")
    private Integer idContaOrigem;

    @Column(name = "id_conta_destino")
    private Integer idContaDestino;

    @Column(name = "id_cliente_origem")
    private Integer idClienteOrigem;

    @Column(name = "id_cliente_destino")
    private Integer idClienteDestino;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getIdContaOrigem() {
        return idContaOrigem;
    }

    public void setIdContaOrigem(Integer idContaOrigem) {
        this.idContaOrigem = idContaOrigem;
    }

    public Integer getIdContaDestino() {
        return idContaDestino;
    }

    public void setIdContaDestino(Integer idContaDestino) {
        this.idContaDestino = idContaDestino;
    }

    public Integer getIdClienteOrigem() {
        return idClienteOrigem;
    }

    public void setIdClienteOrigem(Integer idClienteOrigem) {
        this.idClienteOrigem = idClienteOrigem;
    }

    public Integer getIdClienteDestino() {
        return idClienteDestino;
    }

    public void setIdClienteDestino(Integer idClienteDestino) {
        this.idClienteDestino = idClienteDestino;
    }
}
