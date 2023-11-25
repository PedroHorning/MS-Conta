package com.example.msaccount.dto;

public record TransactionRequestDTO(Long idClienteOrigem, Long idClinteDestino, Long idContaOrigem, Long idContaDestino,
                String tipo, Double valor) {
}
