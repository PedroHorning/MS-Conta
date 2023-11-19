package br.net.msconta.rabbimq;

import br.net.msconta.controller.ContaController;
import br.net.msconta.model.Conta;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountListener {
    @Autowired
    private ContaController contaController;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "saga.autocadastro.account-req")
    public void onAutoRegister(Conta data) {
        Conta contaResponse = this.contaController.createConta(data);
        //CustomerResponseDTO customerResponseDTO = customerResponse.getBody();

        String routingKey = "saga.autocadastro.account-res";
        rabbitTemplate.convertAndSend(routingKey, contaResponse);
    }
}
