package br.com.pedido.infrastructure.broker.rabbit;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    static final String EXCHANGE_NAME = "pedido";
    static final String QUEUE_NAME = "pedido-queue";
    static final String ROUTING_KEY = "pedido-routing-key";


    static final String TRANSACAO_REQUEST_QUEUE = "transacao-request-queue";
    static final String TRANSACAO_REQUEST_ROUTING_KEY = "transacao-request-routing-key";
    static final String TRANSACAO_REQUEST_EXCHANGE = "transacao-request-exchange";
    static final String RPC_TRANSACAO_REQUEST_REPLY_QUEUE = "rpc-transacao-request-reply-queue";

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter jsonMessageConverter) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter);
        return rabbitTemplate;
    }


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
    public Binding rpcBinding() {
        return BindingBuilder
                .bind(transacaoRequestQueue())
                .to(rpcExchange())
                .with(TRANSACAO_REQUEST_ROUTING_KEY);
    }

    @Bean
    public Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder
                .bind(queue)
                .to(exchange)
                .with(ROUTING_KEY);
    }

}
