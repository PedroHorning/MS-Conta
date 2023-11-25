package com.example.msaccount.dto;

public record AccountRequestDTO(Long idCliente, Long idGerente, Double saldo, Double limite, String numeroConta) {
}
