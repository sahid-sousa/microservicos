package br.com.pedido.infrastructure.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(ConciliarPedidoJob.class)
                .withIdentity("conciliarPedidoJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger jobTrigger(JobDetail jobDetail) {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/5 * 1/1 * ? *"); // Executa a cada 5 segundos

        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity("conciliarPedidoTrigger")
                .withSchedule(scheduleBuilder)
                .build();
    }

}
