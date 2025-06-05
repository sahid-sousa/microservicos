package br.com.gateway.venda.infrastructure.route;

import br.com.commons.dto.venda.VendaDetailDto;
import br.com.gateway.venda.infrastructure.broker.Producer;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@Component
public class FileReader implements Processor {

    Producer<VendaDetailDto> producer;

    public FileReader(Producer<VendaDetailDto> producer) {
        this.producer = producer;
    }

    @Override
    public void process(Exchange exchange)  {
        InputStream inputStream = exchange.getIn().getBody(InputStream.class);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        Stream<String> lines = reader.lines();
        lines.forEach(line -> {
            String[] fields = line.split(";");
            if (Objects.equals(fields[0], "001")) {
                log.info(line);
            } else {
                log.info(Arrays.toString(fields));
                processContent(fields);
            }
        });

    }

    public void processContent(String[] fields) {
        VendaDetailDto venda = convertContentToVendaDetailDto(fields);
        producer.enviar(venda);
    }

    public VendaDetailDto convertContentToVendaDetailDto(String[] fields) {
        try {
            return new VendaDetailDto(
                    "",
                    stringToDate(fields[1]),
                    fields[2],
                    fields[3],
                    Integer.parseInt(fields[4]),
                    fields[5],
                    Integer.parseInt(fields[6]),
                    fields[7],
                    new BigDecimal(fields[8].replace(",", ".")),
                    new BigDecimal(fields[9].replace(",", "."))
            );
        } catch (ParseException | NumberFormatException e) {
            throw new RuntimeException("Erro ao converter para VendaDetailDto", e);
        }
    }

    public Date stringToDate(String date) throws ParseException {
        final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("ddMMyyyy");
        return DATE_FORMAT.parse(date);
    }



}
