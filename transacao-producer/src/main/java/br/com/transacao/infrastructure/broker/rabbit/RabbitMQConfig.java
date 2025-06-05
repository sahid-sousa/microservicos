package br.com.transacao.infrastructure.broker.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    static final String EXCHANGE_NAME = "transacao";
    static final String QUEUE_NAME = "transacao-queue";
    static final String ROUTING_KEY = "transacao-routing-key";

    static final String TRANSACAO_REQUEST_QUEUE = "transacao-request-queue";
    static final String TRANSACAO_REQUEST_ROUTING_KEY = "transacao-request-routing-key";
    static final String TRANSACAO_REQUEST_EXCHANGE = "transacao-request-exchange";
    static final String RPC_TRANSACAO_REQUEST_REPLY_QUEUE = "rpc-transacao-request-reply-queue";

    static final String VENDA_REQUEST_QUEUE = "venda-request-queue";
    static final String VENDA_REQUEST_ROUTING_KEY = "venda-request-routing-key";
    static final String VENDA_REQUEST_EXCHANGE = "venda-request-exchange";
    static final String RPC_VENDA_REQUEST_REPLY_QUEUE = "rpc-venda-request-reply-queue";

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

    @Bean
    DirectExchange rpcTransacaoExchange() {
        return new DirectExchange(TRANSACAO_REQUEST_EXCHANGE);
    }

    @Bean
    public Queue transacaoRequestQueue() {
        return new Queue(TRANSACAO_REQUEST_QUEUE, true);
    }

    @Bean
    public Queue replyTransacaoQueueRpc() {
        return new Queue(RPC_TRANSACAO_REQUEST_REPLY_QUEUE, true);
    }

    @Bean
    public Binding rpcTransacaoBinding() {
        return BindingBuilder
                .bind(transacaoRequestQueue())
                .to(rpcTransacaoExchange())
                .with(TRANSACAO_REQUEST_ROUTING_KEY);
    }

    @Bean
    DirectExchange rpcVendaExchange() {
        return new DirectExchange(VENDA_REQUEST_EXCHANGE);
    }

    @Bean
    public Queue vendaRequestQueue() {
        return new Queue(VENDA_REQUEST_QUEUE, true);
    }

    @Bean
    public Queue replyVendaQueueRpc() {
        return new Queue(RPC_VENDA_REQUEST_REPLY_QUEUE, true);
    }

    @Bean
    public Binding rpcVendaBinding() {
        return BindingBuilder
                .bind(vendaRequestQueue())
                .to(rpcVendaExchange())
                .with(VENDA_REQUEST_ROUTING_KEY);
    }
}
