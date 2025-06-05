package br.com.transacao.application.usecases.transacao;

import br.com.commons.dto.transacao.TransacaoDto;
import br.com.transacao.adpaters.gateway.transacao.TransacaoGateway;
import br.com.transacao.domain.entities.StatusTransacao;
import br.com.transacao.domain.entities.Transacao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CriarTransacaoTest {

    @Autowired
    CriarTransacao criarTransacao;

    @Test
    public void deveCriarTransacao() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        TransacaoDto dto = new TransacaoDto(
                "LOJA123",
                "PEDIDO456",
                "uuid-abc-123",
                formatter.parse("07/05/2025"),
                3,
                new BigDecimal("150.00"),
                new BigDecimal("2.50"),
                "CREDITO",
                "123456******7890",
                "AUTH123456",
                987654,
                "VISA"
        );

        Transacao transacao = criarTransacao.criarTransacao(dto);
        Assertions.assertNotNull(transacao);

    }

}
