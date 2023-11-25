package br.net.msconta.dto;

import br.net.msconta.model.Conta;

import java.time.LocalDate;

public record AccountResponseDTO(Integer id, String numeroConta, LocalDate dataCriacao, Double limite, Long idCliente, Long idGerente, Double saldo) {
    public AccountResponseDTO(Conta conta) {
        this(conta.getId(), conta.getNumeroConta(), conta.getDataCriacao(),
                conta.getLimite(), conta.getIdCliente(),
                conta.getIdGerente(), conta.getSaldo());
    }
}
