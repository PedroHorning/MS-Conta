package com.example.msaccount.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.msaccount.dto.AccountRequestDTO;
import com.example.msaccount.dto.AccountResponseDTO;
import com.example.msaccount.model.Account;
import com.example.msaccount.service.AccountCommandService;
import com.example.msaccount.service.AccountQueryService;

@RestController
@RequestMapping("/api/conta")
public class AccountController {
    @Autowired
    private AccountQueryService accountQueryService;

    @Autowired
    private AccountCommandService accountCommandService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<AccountResponseDTO>> getAll() {
        List<Account> accountList = accountQueryService.getAllAccount();
        List<AccountResponseDTO> accountListDTO = accountList.stream()
                .map(AccountResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(accountListDTO);
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<AccountResponseDTO> saveAccount(@ModelAttribute AccountRequestDTO data) {
        Account accountData = new Account(data);
        accountData = accountCommandService.createAccount(accountData);
        AccountResponseDTO responseDTO = new AccountResponseDTO(accountData);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable Long id) {
        Optional<Account> existingAccount = accountQueryService.getAcccontById(id);

        if (existingAccount.isPresent()) {
            Account account = existingAccount.get();
            AccountResponseDTO responseDTO = new AccountResponseDTO(account);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> updateAccount(@PathVariable Long id,
            @ModelAttribute AccountRequestDTO data) {
        Optional<Account> existingAccount = accountQueryService.getAcccontById(id);

        if (existingAccount.isPresent()) {
            Account account = existingAccount.get();
            Account updatedAccount = accountCommandService.updateAccount(account, data);
            AccountResponseDTO responseDTO = new AccountResponseDTO(updatedAccount);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<AccountResponseDTO> deleteAccount(@PathVariable Long id) {
        Optional<Account> existingAccount = accountQueryService.getAcccontById(id);
        if (existingAccount.isPresent()) {
            Account account = existingAccount.get();
            accountCommandService.deleteAccount(account);
            AccountResponseDTO responseDTO = new AccountResponseDTO(account);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
