package br.com.transacao.application.usecases.parcela;

import br.com.commons.dto.transacao.ParcelaDto;
import br.com.commons.dto.transacao.TransacaoDto;
import br.com.transacao.application.usecases.transacao.CriarTransacao;
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
public class CalcularParcelasTest {

    @Autowired
    CriarTransacao criarTransacao;
    @Autowired
    CalcularParcelas calcularParcelas;


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

        List<ParcelaDto> parcelas =  calcularParcelas.calcular(transacao);

        Assertions.assertEquals(1, parcelas.size());

        Assertions.assertEquals(new BigDecimal("100.00"), parcelas.get(0).valorBruto());
        Assertions.assertEquals(new BigDecimal("97.50"), parcelas.get(0).valorLiquido());
        Assertions.assertEquals(new BigDecimal("2.50"),  parcelas.get(0).valorDesconto());
        Assertions.assertEquals(formatter.parse("08/05/2025"), parcelas.get(0).data());

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

        List<ParcelaDto> parcelas =  calcularParcelas.calcular(transacao);

        Assertions.assertEquals(3, parcelas.size());

        Assertions.assertEquals(new BigDecimal("33.34"), parcelas.get(0).valorBruto());
        Assertions.assertEquals(new BigDecimal("32.50"), parcelas.get(0).valorLiquido());
        Assertions.assertEquals(new BigDecimal("0.84"),  parcelas.get(0).valorDesconto());
        Assertions.assertEquals(formatter.parse("18/06/2025"), parcelas.get(0).data());

        Assertions.assertEquals(new BigDecimal("33.33"), parcelas.get(1).valorBruto());
        Assertions.assertEquals(new BigDecimal("32.50"), parcelas.get(1).valorLiquido());
        Assertions.assertEquals(new BigDecimal("0.83"),  parcelas.get(1).valorDesconto());
        Assertions.assertEquals(formatter.parse("30/07/2025"), parcelas.get(1).data());

        Assertions.assertEquals(new BigDecimal("33.33"), parcelas.get(2).valorBruto());
        Assertions.assertEquals(new BigDecimal("32.50"), parcelas.get(2).valorLiquido());
        Assertions.assertEquals(new BigDecimal("0.83"),  parcelas.get(2).valorDesconto());
        Assertions.assertEquals(formatter.parse("10/09/2025"), parcelas.get(2).data());

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


        List<ParcelaDto> parcelas =  calcularParcelas.calcular(transacao);

        Assertions.assertEquals(1, parcelas.size());

        Assertions.assertEquals(new BigDecimal("100.00"), parcelas.get(0).valorBruto());
        Assertions.assertEquals(new BigDecimal("97.50"), parcelas.get(0).valorLiquido());
        Assertions.assertEquals(new BigDecimal("2.50"),  parcelas.get(0).valorDesconto());
        Assertions.assertEquals(formatter.parse("18/06/2025"), parcelas.get(0).data());

    }
}
