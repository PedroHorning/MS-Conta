package com.example.msaccount.dto;

import java.time.LocalDateTime;

import com.example.msaccount.model.Transaction;

public record TransactionResponseDTO(
        Long id, Long idClienteDestino,
        Long idClienteOrigem, Long idContaDestino,
        Long idContaOrigem,
        Double valor,
        LocalDateTime dataHora,
        String tipo) {

    public TransactionResponseDTO(Transaction movimentacao) {
        this(movimentacao.getId(), movimentacao.getIdClienteDestino(), movimentacao.getIdClienteOrigem(),
                movimentacao.getIdContaDestino(), movimentacao.getIdContaOrigem(),
                movimentacao.getValor(), movimentacao.getDataHora(), movimentacao.getTipo());
    }
}
