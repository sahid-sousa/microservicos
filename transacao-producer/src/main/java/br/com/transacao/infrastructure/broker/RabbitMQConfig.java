package br.com.transacao.infrastructure.broker;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    static final String TRANSACAO_REQUEST_QUEUE = "transacao-request-queue";
    static final String TRANSACAO_REQUEST_ROUTING_KEY = "transacao-request-routing-key";
    static final String TRANSACAO_REQUEST_EXCHANGE = "transacao-request-exchange";
    static final String RPC_TRANSACAO_REQUEST_REPLY_QUEUE = "rpc-transacao-request-reply-queue";

    @Bean
    DirectExchange rpcExchange() {
        return new DirectExchange(TRANSACAO_REQUEST_EXCHANGE);
    }

    @Bean
    public Queue transacaoRequestQueue() {
        return new Queue(TRANSACAO_REQUEST_QUEUE, true);
    }

    @Bean
    public Queue replyQueueRpc() {
        return new Queue(RPC_TRANSACAO_REQUEST_REPLY_QUEUE, true);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Binding rpcBinding() {
        return BindingBuilder
                .bind(transacaoRequestQueue())
                .to(rpcExchange())
                .with(TRANSACAO_REQUEST_ROUTING_KEY);
    }
}
