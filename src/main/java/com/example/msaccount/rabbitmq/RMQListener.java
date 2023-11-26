package com.example.msaccount.rabbitmq;

import com.example.msaccount.dto.AccountRequestDTO;
import com.example.msaccount.dto.AccountResponseDTO;
import com.example.msaccount.model.Account;
import com.example.msaccount.service.AccountCommandService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RMQListener {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private AccountCommandService accountCommandService;

    @RabbitListener(queues = "saga.autocadastro.account-req")
    public void onAutoRegisterCustomerRes(AccountRequestDTO data) {
        Account account = new Account(data);
        Account createdAccount = accountCommandService.createAccount(account);
        AccountResponseDTO accountResponseDTO =  new AccountResponseDTO(createdAccount);

        String routingKey = "saga.autocadastro.account-res";
        rabbitTemplate.convertAndSend(routingKey, accountResponseDTO);
    }
}
