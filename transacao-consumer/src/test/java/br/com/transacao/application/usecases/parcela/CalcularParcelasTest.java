package br.com.transacao.application.usecases.parcela;

import br.com.commons.dto.transacao.ParcelaDto;
import br.com.commons.dto.transacao.TransacaoDto;
import br.com.transacao.application.usecases.transacao.CriarTransacao;
import br.com.transacao.domain.entities.StatusTransacao;
import br.com.transacao.domain.entities.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
public class CalcularParcelasTest {

    @Mock
    CriarTransacao criarTransacao;
    @Mock
    CalcularParcelas calcularParcelas;

    SimpleDateFormat formatter;

    @BeforeEach
    public void setup() {
        formatter = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Test
    @DisplayName("Test Criar Parcelas Transacao Debito")
    public void testCriarParcelasTransacaoDebito() throws ParseException {
        //Given
        TransacaoDto transacaoDto = new TransacaoDto(
                "LOJA123", "PEDIDO456",
                "uuid-abc-123", formatter.parse("07/05/2025"),
                1, new BigDecimal("100.00"),
                new BigDecimal("1"), "DEBITO",
                "123456******7890", "AUTH123456",
                987654, "VISA"
        );

        Transacao transacao = new Transacao();
        transacao.setCodigoLoja("LOJA123");
        transacao.setCodigoPedido("PEDIDO456");
        transacao.setValor(new BigDecimal("100.00"));
        transacao.setTaxa(new BigDecimal("1"));
        transacao. setStatus(StatusTransacao.PENDENTE);
        transacao.setCartao("123456******7890");
        transacao.setCodigoAutorizacao("AUTH123456");
        transacao.setNsu(987654);
        transacao.setBandeira("VISA");
        transacao.setParcelas(new ArrayList<>());
        transacao.setData(formatter.parse("07/05/2025"));
        transacao. setTipoTransacao("DEBITO");
        transacao.setQuantidadeParcelas(1);

        ParcelaDto parcelaDebitoDto = new ParcelaDto(
                formatter.parse("08/05/2025"),1,
                new BigDecimal("100.00"), new BigDecimal("99.00"),
                new BigDecimal("1")
        );

        given(criarTransacao.criarTransacao(any())).willReturn(transacao);
        given(calcularParcelas.calcular(any())).willReturn(List.of(parcelaDebitoDto));

        //When
        Transacao entity = criarTransacao.criarTransacao(transacaoDto);
        List<ParcelaDto> parcelaDebitoDtoList = calcularParcelas.calcular(entity);

        //Then
        assertNotNull(entity);
        assertEquals(new BigDecimal("100.00"), entity.getValor());
        assertEquals(new BigDecimal("1"),  entity.getTaxa());
        assertEquals(formatter.parse("07/05/2025"), entity.getData());

        assertFalse(parcelaDebitoDtoList.isEmpty());
        assertEquals(new BigDecimal("100.00"), parcelaDebitoDtoList.get(0).valorBruto());
        assertEquals(new BigDecimal("99.00"), parcelaDebitoDtoList.get(0).valorLiquido());
        assertEquals(new BigDecimal("1"),  parcelaDebitoDtoList.get(0).valorDesconto());
        assertEquals(formatter.parse("08/05/2025"), parcelaDebitoDtoList.get(0).data());
    }

    @Test
    @DisplayName("Test criar Parcelas Transacao Credito")
    public void testCriarParcelasTransacaoCredito() throws ParseException {
        //Given
        TransacaoDto transacaoDto = new TransacaoDto(
                "LOJA123", "PEDIDO456",
                "uuid-abc-123", formatter.parse("07/05/2025"),
                2, new BigDecimal("100.00"),
                new BigDecimal("1"), "DEBITO",
                "123456******7890", "AUTH123456",
                987654, "VISA"
        );

        Transacao transacao = new Transacao();
        transacao.setCodigoLoja("LOJA123");
        transacao.setCodigoPedido("PEDIDO456");
        transacao.setValor(new BigDecimal("100.00"));
        transacao.setTaxa(new BigDecimal("1"));
        transacao. setStatus(StatusTransacao.PENDENTE);
        transacao.setCartao("123456******7890");
        transacao.setCodigoAutorizacao("AUTH123456");
        transacao.setNsu(987654);
        transacao.setBandeira("VISA");
        transacao.setParcelas(new ArrayList<>());
        transacao.setData(formatter.parse("07/05/2025"));
        transacao. setTipoTransacao("CREDITO");
        transacao.setQuantidadeParcelas(2);

        ParcelaDto parcelaCreditoUmDto = new ParcelaDto(
                formatter.parse("18/06/2025"),1,
                new BigDecimal("50.00"), new BigDecimal("49.50"),
                new BigDecimal("0.50")
        );

        ParcelaDto parcelaCreditoDoisDto = new ParcelaDto(
                formatter.parse("30/07/2025"),1,
                new BigDecimal("50.00"), new BigDecimal("49.50"),
                new BigDecimal("0.50")
        );

        given(criarTransacao.criarTransacao(any())).willReturn(transacao);
        given(calcularParcelas.calcular(any())).willReturn(List.of(parcelaCreditoUmDto, parcelaCreditoDoisDto));

        //When
        Transacao entity = criarTransacao.criarTransacao(transacaoDto);
        List<ParcelaDto> parcelaDebitoDtoList = calcularParcelas.calcular(entity);

        //Then
        assertNotNull(entity);
        assertEquals(new BigDecimal("100.00"), entity.getValor());
        assertEquals(new BigDecimal("1"),  entity.getTaxa());
        assertEquals(formatter.parse("07/05/2025"), entity.getData());

        assertFalse(parcelaDebitoDtoList.isEmpty());
        assertEquals(new BigDecimal("50.00"), parcelaDebitoDtoList.get(0).valorBruto());
        assertEquals(new BigDecimal("49.50"), parcelaDebitoDtoList.get(0).valorLiquido());
        assertEquals(new BigDecimal("0.50"),  parcelaDebitoDtoList.get(0).valorDesconto());
        assertEquals(formatter.parse("18/06/2025"), parcelaDebitoDtoList.get(0).data());

        assertEquals(new BigDecimal("50.00"), parcelaDebitoDtoList.get(1).valorBruto());
        assertEquals(new BigDecimal("49.50"), parcelaDebitoDtoList.get(1).valorLiquido());
        assertEquals(new BigDecimal("0.50"),  parcelaDebitoDtoList.get(1).valorDesconto());
        assertEquals(formatter.parse("30/07/2025"), parcelaDebitoDtoList.get(1).data());

    }

    @Test
    @DisplayName("Test Criar Parcelas Transacao Avista Credito")
    public void testCriarParcelasTransacaoAvistaCredito() throws ParseException {
        //Given
        TransacaoDto transacaoDto = new TransacaoDto(
                "LOJA123", "PEDIDO456",
                "uuid-abc-123", formatter.parse("07/05/2025"),
                1, new BigDecimal("100.00"),
                new BigDecimal("1"), "DEBITO",
                "123456******7890", "AUTH123456",
                987654, "VISA"
        );

        Transacao transacao = new Transacao();
        transacao.setCodigoLoja("LOJA123");
        transacao.setCodigoPedido("PEDIDO456");
        transacao.setValor(new BigDecimal("100.00"));
        transacao.setTaxa(new BigDecimal("1"));
        transacao. setStatus(StatusTransacao.PENDENTE);
        transacao.setCartao("123456******7890");
        transacao.setCodigoAutorizacao("AUTH123456");
        transacao.setNsu(987654);
        transacao.setBandeira("VISA");
        transacao.setParcelas(new ArrayList<>());
        transacao.setData(formatter.parse("07/05/2025"));
        transacao. setTipoTransacao("CREDITO");
        transacao.setQuantidadeParcelas(1);

        ParcelaDto parcelaDebitoDto = new ParcelaDto(
                formatter.parse("18/06/2025"),1,
                new BigDecimal("100.00"), new BigDecimal("99.00"),
                new BigDecimal("1")
        );

        given(criarTransacao.criarTransacao(any())).willReturn(transacao);
        given(calcularParcelas.calcular(any())).willReturn(List.of(parcelaDebitoDto));

        //When
        Transacao entity = criarTransacao.criarTransacao(transacaoDto);
        List<ParcelaDto> parcelaDebitoDtoList = calcularParcelas.calcular(entity);

        //Then
        assertNotNull(entity);
        assertEquals(new BigDecimal("100.00"), entity.getValor());
        assertEquals(new BigDecimal("1"),  entity.getTaxa());
        assertEquals(formatter.parse("07/05/2025"), entity.getData());

        assertFalse(parcelaDebitoDtoList.isEmpty());
        assertEquals(new BigDecimal("100.00"), parcelaDebitoDtoList.get(0).valorBruto());
        assertEquals(new BigDecimal("99.00"), parcelaDebitoDtoList.get(0).valorLiquido());
        assertEquals(new BigDecimal("1"),  parcelaDebitoDtoList.get(0).valorDesconto());
        assertEquals(formatter.parse("18/06/2025"), parcelaDebitoDtoList.get(0).data());
    }
}
