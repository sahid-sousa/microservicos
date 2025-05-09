package br.com.transacao.application.usecases.parcela;

import br.com.commons.dto.transacao.ParcelaDto;
import br.com.commons.dto.transacao.TransacaoDto;
import br.com.transacao.adpaters.gateway.parcela.ParcelaGateway;
import br.com.transacao.adpaters.gateway.transacao.TransacaoGateway;
import br.com.transacao.application.usecases.transacao.CriarTransacao;
import br.com.transacao.domain.entities.Parcela;
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
import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class CriarParcelaTest {

    @Autowired
    CriarParcela criarParcela;
    @Autowired
    CriarTransacao criarTransacao;
    @Autowired
    CalcularParcelas calcularParcelas;

    @Autowired
    TransacaoGateway transacaoGateway;
    @Autowired
    ParcelaGateway parcelaGateway;

    @Test
    public void deveCriarParcelasTransacaoDebito() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        TransacaoDto dto = new TransacaoDto(
                "LOJA123",
                "PEDIDO456",
                "uuid-abc-123",
                formatter.parse("07/05/2025"),
                1,
                new BigDecimal("100.00"),
                new BigDecimal("2.50"),
                "DEBITO",
                "123456******7890",
                "AUTH123456",
                987654,
                "VISA"
        );

        Transacao transacao = criarTransacao.criarTransacao(dto);
        transacaoGateway.save(transacao);

        List<ParcelaDto> parcelas =  calcularParcelas.calcular(transacao);
        Parcela parcela = criarParcela.criar(transacao, parcelas.get(0));
        parcelaGateway.save(parcela);

        Assertions.assertEquals(1, parcelas.size());
        Assertions.assertNotNull(parcela.getId());

        Assertions.assertEquals(new BigDecimal("100.00"), parcela.getValorBruto());
        Assertions.assertEquals(new BigDecimal("97.50"), parcela.getValorLiquido());
        Assertions.assertEquals(new BigDecimal("2.50"),  parcela.getValorDesconto());
        Assertions.assertEquals(formatter.parse("08/05/2025"), parcela.getData());

    }

    @Test
    public void deveCriarParcelasTransacaoCredito() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        TransacaoDto dto = new TransacaoDto(
                "LOJA123",
                "PEDIDO456",
                "uuid-abc-123",
                formatter.parse("07/05/2025"),
                3,
                new BigDecimal("100.00"),
                new BigDecimal("2.50"),
                "CREDITO",
                "123456******7890",
                "AUTH123456",
                987654,
                "VISA"
        );

        Transacao transacao = criarTransacao.criarTransacao(dto);
        transacaoGateway.save(transacao);

        List<ParcelaDto> parcelas =  calcularParcelas.calcular(transacao);

        Parcela p1 = criarParcela.criar(transacao, parcelas.get(0));
        parcelaGateway.save(p1);
        Parcela p2 = criarParcela.criar(transacao, parcelas.get(1));
        parcelaGateway.save(p2);
        Parcela p3 = criarParcela.criar(transacao, parcelas.get(2));
        parcelaGateway.save(p3);

        Assertions.assertEquals(3, parcelas.size());

        Assertions.assertNotNull(p1.getId());
        Assertions.assertNotNull(p2.getId());
        Assertions.assertNotNull(p3.getId());

        Assertions.assertEquals(new BigDecimal("33.34"), p1.getValorBruto());
        Assertions.assertEquals(new BigDecimal("32.50"), p1.getValorLiquido());
        Assertions.assertEquals(new BigDecimal("0.84"),  p1.getValorDesconto());
        Assertions.assertEquals(formatter.parse("18/06/2025"), p1.getData());

        Assertions.assertEquals(new BigDecimal("33.33"), p2.getValorBruto());
        Assertions.assertEquals(new BigDecimal("32.50"), p2.getValorLiquido());
        Assertions.assertEquals(new BigDecimal("0.83"),  p2.getValorDesconto());
        Assertions.assertEquals(formatter.parse("30/07/2025"), p2.getData());

        Assertions.assertEquals(new BigDecimal("33.33"), p3.getValorBruto());
        Assertions.assertEquals(new BigDecimal("32.50"), p3.getValorLiquido());
        Assertions.assertEquals(new BigDecimal("0.83"),  p3.getValorDesconto());
        Assertions.assertEquals(formatter.parse("10/09/2025"), p3.getData());


    }

    @Test
    public void deveCriarParcelasTransacaoAvistaCredito() throws ParseException {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        TransacaoDto dto = new TransacaoDto(
                "LOJA123",
                "PEDIDO456",
                "uuid-abc-123",
                formatter.parse("07/05/2025"),
                1,
                new BigDecimal("100.00"),
                new BigDecimal("2.50"),
                "CREDITO",
                "123456******7890",
                "AUTH123456",
                987654,
                "VISA"
        );

        Transacao transacao = criarTransacao.criarTransacao(dto);
        transacaoGateway.save(transacao);

        List<ParcelaDto> parcelas =  calcularParcelas.calcular(transacao);
        Parcela parcela = criarParcela.criar(transacao, parcelas.get(0));
        parcelaGateway.save(parcela);

        Assertions.assertEquals(1, parcelas.size());
        Assertions.assertNotNull(parcela.getId());
        Assertions.assertNotNull(transacao.getId());

        Assertions.assertEquals(new BigDecimal("100.00"), parcela.getValorBruto());
        Assertions.assertEquals(new BigDecimal("97.50"), parcela.getValorLiquido());
        Assertions.assertEquals(new BigDecimal("2.50"),  parcela.getValorDesconto());
        Assertions.assertEquals(formatter.parse("18/06/2025"), parcelas.get(0).data());

    }

}
