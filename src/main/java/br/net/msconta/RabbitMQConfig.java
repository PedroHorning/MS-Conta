package br.net.msconta;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.command.exchange}")
    private String commandExchange;

    @Value("${rabbitmq.query.queue}")
    private String queryQueue;

    @Bean
    public TopicExchange commandExchange() {
        return new TopicExchange(commandExchange);
    }

    @Bean
    public Queue queryQueue() {
        return new Queue(queryQueue);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("query.#");
    }
}

