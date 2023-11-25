package com.example.msaccount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.msaccount.dto.TransactionRequestDTO;
import com.example.msaccount.model.Transaction;
import com.example.msaccount.repository.TransactionRepository;

@Service
public class TransactionCommandService {
    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction conta) {
        return transactionRepository.save(conta);
    }

    public Transaction updateTransaction(Transaction transaction, TransactionRequestDTO data) {
        transaction.setDataHora(transaction.getDataHora());
        transaction.setIdClienteDestino(data.idClinteDestino());
        transaction.setIdClienteOrigem(data.idClienteOrigem());
        transaction.setIdContaDestino(data.idContaDestino());
        transaction.setIdContaOrigem(data.idContaOrigem());
        transaction.setValor(data.valor());
        transaction.setTipo(data.tipo());
        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Transaction transaction) {
        if (transaction != null) {
            transactionRepository.delete(transaction);
        }
    }
}
