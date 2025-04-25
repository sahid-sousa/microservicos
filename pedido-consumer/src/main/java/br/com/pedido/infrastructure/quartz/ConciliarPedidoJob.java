package br.com.pedido.infrastructure.quartz;

import br.com.pedido.application.usecases.ConciliarPedido;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@DisallowConcurrentExecution
public class ConciliarPedidoJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(ConciliarPedidoJob.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final ConciliarPedido conciliarPedido;

    public ConciliarPedidoJob(ConciliarPedido conciliarPedido) {
        this.conciliarPedido = conciliarPedido;
    }

    @Override
    public void execute(JobExecutionContext context) {
        LocalDateTime now = LocalDateTime.now();
        conciliarPedido.executar();
        logger.info("Tarefa ConciliarPedidoJob executada em: {}", now.format(formatter));
    }
}