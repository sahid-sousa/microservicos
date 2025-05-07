package br.com.transacao.infrastructure.broker;

import br.com.commons.dto.transacao.TransacaoDto;
import br.com.transacao.application.usecases.transacao.CriarTransacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TransacaoConsumer  {

    private final CriarTransacao criarTransacao;

    public TransacaoConsumer(CriarTransacao criarTransacao) {
        this.criarTransacao = criarTransacao;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void consumer(TransacaoDto transacaoDto) {
        criarTransacao.executar(transacaoDto);
    }
}
