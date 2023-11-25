package com.example.msaccount.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.msaccount.model.Account;
import com.example.msaccount.repository.AccountRepository;

@Service
public class AccountQueryService {
    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAcccontById(Long id) {
        return accountRepository.findById(id);
    }
}
