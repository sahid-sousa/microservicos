package br.com.transacao.application.usecases.parcela;

import br.com.commons.dto.transacao.ParcelaDto;
import br.com.transacao.domain.entities.Parcela;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CriarParcelaTest {

    @Mock
    CriarParcela criarParcela;

    private SimpleDateFormat formatter;

    @BeforeEach
    public void setup() {
        formatter = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Test
    @DisplayName("Test Criar Parcelas Transacao Debito")
    public void testCriarParcelasTransacaoDebito() throws ParseException {
        //Given
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

        Parcela parcela = new Parcela();
        parcela.setData(formatter.parse("08/05/2025"));
        parcela.setNumero(1);
        parcela.setValorBruto(new BigDecimal("100.00"));
        parcela.setValorLiquido(new BigDecimal("99.00"));
        parcela.setValorDesconto(new BigDecimal("1"));

        given(criarParcela.criar(any(), any())).willReturn(parcela);

        //When
        Parcela parcelaDebito = criarParcela.criar(transacao, parcelaDebitoDto);

        //Then
        assertNotNull(parcelaDebito);
        assertEquals(new BigDecimal("100.00"), parcelaDebito.getValorBruto());
        assertEquals(new BigDecimal("99.00"), parcelaDebito.getValorLiquido());
        assertEquals(new BigDecimal("1"),  parcelaDebito.getValorDesconto());
        assertEquals(formatter.parse("08/05/2025"), parcelaDebito.getData());
    }

    @Test
    @DisplayName("Test Criar Parcelas Transacao Credito Avista")
    public void testCriarParcelasTransacaoCreditoAvista() throws ParseException {
        //Given
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

        Parcela parcela = new Parcela();
        parcela.setData(formatter.parse("18/06/2025"));
        parcela.setNumero(1);
        parcela.setValorBruto(new BigDecimal("100.00"));
        parcela.setValorLiquido(new BigDecimal("99.00"));
        parcela.setValorDesconto(new BigDecimal("1"));

        given(criarParcela.criar(any(), any())).willReturn(parcela);

        //When
        Parcela parcelaDebito = criarParcela.criar(transacao, parcelaDebitoDto);

        //Then
        assertNotNull(parcelaDebito);
        assertEquals(new BigDecimal("100.00"), parcelaDebito.getValorBruto());
        assertEquals(new BigDecimal("99.00"), parcelaDebito.getValorLiquido());
        assertEquals(new BigDecimal("1"),  parcelaDebito.getValorDesconto());
        assertEquals(formatter.parse("18/06/2025"), parcelaDebito.getData());
    }


    @Test
    @DisplayName("Test Criar Parcelas Transacao Credito")
    public void testCriarParcelasTransacaoCredito() throws ParseException {
        //Given
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

        ParcelaDto parcelaUmDto = new ParcelaDto(
                formatter.parse("18/06/2025"),1,
                new BigDecimal("50.00"), new BigDecimal("49.50"),
                new BigDecimal("0.50")
        );


        Parcela parcelaUm = new Parcela();
        parcelaUm.setData(formatter.parse("18/06/2025"));
        parcelaUm.setNumero(1);
        parcelaUm.setValorBruto(new BigDecimal("50.00"));
        parcelaUm.setValorLiquido(new BigDecimal("49.50"));
        parcelaUm.setValorDesconto(new BigDecimal("1"));

        ParcelaDto parcelaDoisDto = new ParcelaDto(
                formatter.parse("30/07/2025"),2,
                new BigDecimal("50.00"), new BigDecimal("49.50"),
                new BigDecimal("0.50")
        );

        Parcela parcelaDois = new Parcela();
        parcelaDois.setData(formatter.parse("30/07/2025"));
        parcelaDois.setNumero(2);
        parcelaDois.setValorBruto(new BigDecimal("50.00"));
        parcelaDois.setValorLiquido(new BigDecimal("49.50"));
        parcelaDois.setValorDesconto(new BigDecimal("1"));

        //When
        given(criarParcela.criar(any(), any())).willReturn(parcelaUm);
        Parcela parcelaUmCredito = criarParcela.criar(transacao, parcelaUmDto);

        given(criarParcela.criar(any(), any())).willReturn(parcelaDois);
        Parcela parcelaDoisCredito = criarParcela.criar(transacao, parcelaDoisDto);

        //Then
        assertNotNull(parcelaUmCredito);
        assertEquals(new BigDecimal("50.00"), parcelaUmCredito.getValorBruto());
        assertEquals(new BigDecimal("49.50"), parcelaUmCredito.getValorLiquido());
        assertEquals(new BigDecimal("1"),  parcelaUmCredito.getValorDesconto());
        assertEquals(1, parcelaUmCredito.getNumero());
        assertEquals(formatter.parse("18/06/2025"), parcelaUmCredito.getData());

        assertNotNull(parcelaDoisCredito);
        assertEquals(new BigDecimal("50.00"), parcelaDoisCredito.getValorBruto());
        assertEquals(new BigDecimal("49.50"), parcelaDoisCredito.getValorLiquido());
        assertEquals(new BigDecimal("1"),  parcelaDoisCredito.getValorDesconto());
        assertEquals(2, parcelaDoisCredito.getNumero());
        assertEquals(formatter.parse("30/07/2025"), parcelaDoisCredito.getData());
    }

}