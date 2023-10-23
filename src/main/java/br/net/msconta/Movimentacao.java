package br.net.msconta;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Movimentacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    private String tipo; // depósito, saque, transferência

    private String clienteOrigemDestino; // preencher em caso de transferência

    private double valor;

    @ManyToOne
    private Conta conta;

    // Getters e Setters

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

    public String getClienteOrigemDestino() {
        return clienteOrigemDestino;
    }

    public void setClienteOrigemDestino(String clienteOrigemDestino) {
        this.clienteOrigemDestino = clienteOrigemDestino;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    @Override
    public String toString() {
        return "Movimentacao{" +
                "id=" + id +
                ", dataHora=" + dataHora +
                ", tipo='" + tipo + '\'' +
                ", clienteOrigemDestino='" + clienteOrigemDestino + '\'' +
                ", valor=" + valor +
                ", conta=" + conta +
                '}';
    }
}
