package com.example.msaccount.dto;

import java.time.LocalDate;

import com.example.msaccount.model.Account;

public record AccountResponseDTO(Long id, String numeroConta, LocalDate dataCriacao, Double limite, Long idCliente,
        Long idGerente, Double saldo) {
    public AccountResponseDTO(Account conta) {
        this(conta.getId(), conta.getNumeroConta(), conta.getDataCriacao(),
                conta.getLimite(), conta.getIdCliente(),
                conta.getIdGerente(), conta.getSaldo());
    }
}
