package br.com.gateway.venda.infrastructure.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class VendaGatewayRoute extends RouteBuilder {

    FileReader fileReader;

    public VendaGatewayRoute(
            FileReader fileReader
    ) {
        this.fileReader = fileReader;
    }

    @Override
    public void configure() throws Exception {
        from("sftp://{{sftp.username}}@{{sftp.hostname}}:{{sftp.port}}/{{sftp.remote.directory}}?password={{sftp.password}}&delete=true&noop=true")
                .log("Arquivo ${file:name} consumido do SFTP")
                .process(fileReader)
                .log("Arquivo ${file:name} processado");
    }

}
