package com.example.msaccount.controller;

import java.time.LocalDateTime;
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
import com.example.msaccount.dto.TransactionRequestDTO;
import com.example.msaccount.dto.TransactionResponseDTO;
import com.example.msaccount.model.Account;
import com.example.msaccount.model.Transaction;
import com.example.msaccount.repository.TransactionRepository;
import com.example.msaccount.service.AccountCommandService;
import com.example.msaccount.service.AccountQueryService;
import com.example.msaccount.service.TransactionCommandService;
import com.example.msaccount.service.TransactionQueryService;

@RestController
@RequestMapping("/api/movimentacao")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionQueryService transactionQueryService;
    @Autowired
    private TransactionCommandService transactionCommandService;

    @Autowired
    private AccountQueryService accountQueryService;
    @Autowired
    private AccountCommandService accountCommandService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransaction() {
        List<Transaction> transactionList = transactionQueryService.getAllTransaction();
        List<TransactionResponseDTO> transactionListDTO = transactionList.stream()
                .map(TransactionResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(transactionListDTO);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> updateAccount(@PathVariable Long id) {
        Optional<Transaction> existingTransaction = transactionQueryService.getTransactionById(id);

        if (existingTransaction.isPresent()) {
            Transaction transaction = existingTransaction.get();
            TransactionResponseDTO responseDTO = new TransactionResponseDTO(transaction);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping
    public ResponseEntity<TransactionResponseDTO> saveAccount(@ModelAttribute TransactionRequestDTO data) {
        Transaction transactionData = new Transaction(data);
        transactionData = transactionCommandService.createTransaction(transactionData);
        TransactionResponseDTO responseDTO = new TransactionResponseDTO(transactionData);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/deposito/{idConta}")
    public ResponseEntity<TransactionResponseDTO> deposito(@PathVariable Long idConta,
            @ModelAttribute TransactionRequestDTO data) {
        Optional<Account> existingAccount = accountQueryService.getAcccontById(idConta);

        if (existingAccount.isPresent()) {
            Account account = existingAccount.get();
            Transaction transaction = new Transaction(data);

            Double novoSaldo = account.getSaldo() + transaction.getValor();
            account.setSaldo(novoSaldo);

            AccountRequestDTO accountRequestDTO = new AccountRequestDTO(account.getIdCliente(), account.getIdGerente(),
                    account.getSaldo(), account.getLimite(), account.getNumeroConta());
            accountCommandService.updateAccount(account, accountRequestDTO);

            transaction.setTipo("DEPOSITO");
            transaction.setIdContaDestino(idConta);
            transaction.setDataHora(LocalDateTime.now());

            Transaction newTransaction = transactionCommandService.createTransaction(transaction);

            TransactionResponseDTO responseDTO = new TransactionResponseDTO(newTransaction);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/saque/{idConta}")
    public ResponseEntity<TransactionResponseDTO> saque(@PathVariable Long idConta,
            @ModelAttribute TransactionRequestDTO data) {
        Optional<Account> existingAccount = accountQueryService.getAcccontById(idConta);

        if (existingAccount.isPresent()) {

            Account account = existingAccount.get();
            Transaction transaction = new Transaction(data);

            if (account.getSaldo() >= transaction.getValor()) {
                Double novoSaldo = account.getSaldo() - transaction.getValor();
                account.setSaldo(novoSaldo);

                AccountRequestDTO accountRequestDTO = new AccountRequestDTO(account.getIdCliente(),
                        account.getIdGerente(),
                        account.getSaldo(), account.getLimite(), account.getNumeroConta());
                accountCommandService.updateAccount(account, accountRequestDTO);

                transaction.setTipo("SAQUE");
                transaction.setIdContaOrigem(idConta);
                transaction.setDataHora(LocalDateTime.now());

                Transaction newTransaction = transactionCommandService.createTransaction(transaction);

                TransactionResponseDTO responseDTO = new TransactionResponseDTO(newTransaction);
                return ResponseEntity.ok(responseDTO);

            } else {
                throw new SaldoInsuficienteException("Saldo insuficiente para o saque");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/transferencia")
    public ResponseEntity<TransactionResponseDTO> transferencia(@ModelAttribute TransactionRequestDTO data) {

        Optional<Account> existingSourceAccount = accountQueryService.getAcccontById(data.idContaOrigem());
        Optional<Account> existingTargetAccount = accountQueryService.getAcccontById(data.idContaDestino());

        Boolean existingAccounts = existingSourceAccount.isPresent() && existingTargetAccount.isPresent();

        if (existingAccounts) {
            Account sourceAccount = existingSourceAccount.get();
            Account targetAccount = existingTargetAccount.get();
            Transaction transaction = new Transaction(data);

            if (transaction.getValor() <= sourceAccount.getSaldo()) {

                Double novoSaldoOrigem = sourceAccount.getSaldo() - transaction.getValor();
                sourceAccount.setSaldo(novoSaldoOrigem);
                AccountRequestDTO sourceAccountRequestDTO = new AccountRequestDTO(sourceAccount.getIdCliente(),
                        sourceAccount.getIdGerente(),
                        sourceAccount.getSaldo(), sourceAccount.getLimite(), sourceAccount.getNumeroConta());
                accountCommandService.updateAccount(sourceAccount, sourceAccountRequestDTO);

                Double novoSaldoDestino = targetAccount.getSaldo() + transaction.getValor();
                targetAccount.setSaldo(novoSaldoDestino);
                AccountRequestDTO targetAccountRequestDTO = new AccountRequestDTO(targetAccount.getIdCliente(),
                        targetAccount.getIdGerente(),
                        targetAccount.getSaldo(), targetAccount.getLimite(), targetAccount.getNumeroConta());
                accountCommandService.updateAccount(targetAccount, targetAccountRequestDTO);

                transaction.setTipo("TRANSFERENCIA");
                transaction.setIdContaOrigem(sourceAccount.getId());
                transaction.setIdContaDestino(targetAccount.getId());
                transaction.setValor(transaction.getValor());
                transaction.setDataHora(LocalDateTime.now());

                TransactionResponseDTO responseDTO = new TransactionResponseDTO(transaction);
                return ResponseEntity.ok(responseDTO);
            } else {
                throw new TransferenciaInvalidaException("Sem saldo suficiente!");
            }
        } else {
            throw new TransferenciaInvalidaException("Transferência não pôde ser concluída!");
        }
    }

    public class TransferenciaInvalidaException extends RuntimeException {
        public TransferenciaInvalidaException(String message) {
            super(message);
        }
    }

    public class SaldoInsuficienteException extends RuntimeException {
        public SaldoInsuficienteException(String message) {
            super(message);
        }
    }

    @CrossOrigin(origins = "*")
    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> updateTransaction(@PathVariable Long id,
            @ModelAttribute TransactionRequestDTO data) {
        Optional<Transaction> existing = transactionQueryService.getTransactionById(id);
        if (existing.isPresent()) {
            Transaction transaction = existing.get();
            Transaction updatedTransaction = transactionCommandService.updateTransaction(transaction, data);

            TransactionResponseDTO responseDTO = new TransactionResponseDTO(updatedTransaction);
            return ResponseEntity.ok(responseDTO);
        } else {
            ResponseEntity.notFound().build();
        }
        return null;
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> deleteTransacton(@PathVariable Long id) {
        Optional<Transaction> existing = transactionQueryService.getTransactionById(id);
        if (existing.isPresent()) {
            Transaction transaction = existing.get();
            transactionCommandService.deleteTransaction(transaction);
            TransactionResponseDTO responseDTO = new TransactionResponseDTO(transaction);
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
