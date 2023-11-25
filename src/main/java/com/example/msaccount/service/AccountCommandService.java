package com.example.msaccount.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.msaccount.dto.AccountRequestDTO;
import com.example.msaccount.model.Account;
import com.example.msaccount.repository.AccountRepository;

@Service
public class AccountCommandService {
    @Autowired
    private AccountRepository accountRepository;

    public Account createAccount(Account conta) {
        return accountRepository.save(conta);
    }

    public Account updateAccount(Account account, AccountRequestDTO data) {
        account.setLimite(data.limite());
        account.setSaldo(data.saldo());
        account.setIdGerente(data.idGerente());
        account.setIdCliente(data.idCliente());
        account.setNumeroConta(data.numeroConta());
        return accountRepository.save(account);
    }

    public void deleteAccount(Account account) {
        if (account != null) {
            accountRepository.delete(account);
        }
    }
}
