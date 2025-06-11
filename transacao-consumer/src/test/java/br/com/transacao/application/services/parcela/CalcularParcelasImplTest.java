package br.com.transacao.application.services.parcela;

import br.com.commons.dto.transacao.ParcelaDto;
import br.com.transacao.application.usecases.calendario.DiaUtil;
import br.com.transacao.domain.entities.StatusTransacao;
import br.com.transacao.domain.entities.Transacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class CalcularParcelasImplTest {

    @InjectMocks
    CalcularParcelasImpl calcularParcelas;

    @Mock
    DiaUtil diaUtil;

    @Test
    @DisplayName("Test Calcular Parcelas Debito")
    public void testCalcularParcelasDebito() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Transacao transacao = new Transacao();
        transacao.setCodigoLoja("LOJA123");
        transacao.setCodigoPedido("PEDIDO456");
        transacao.setData(formatter.parse("07/05/2025"));
        transacao.setQuantidadeParcelas(1);
        transacao.setValor(new BigDecimal("100.00"));
        transacao.setTaxa(new BigDecimal("1"));
        transacao. setTipoTransacao("DEBITO");
        transacao. setStatus(StatusTransacao.PENDENTE);
        transacao.setCartao("123456*******789");
        transacao.setCodigoAutorizacao("AUTH123456");
        transacao.setNsu(987654);
        transacao.setBandeira("VISA");
        transacao.setParcelas(new ArrayList<>());

        Date dataPagamento = formatter.parse("08/05/2025");

        given(diaUtil.diasUteis(any(), anyInt())).willReturn(dataPagamento);

        //When
        List<ParcelaDto> parcelaDtoList = calcularParcelas.calcular(transacao);

        //Then
       assertFalse(parcelaDtoList.isEmpty());
       assertEquals(dataPagamento, parcelaDtoList.get(0).data());
       assertEquals(new BigDecimal("100.00"), parcelaDtoList.get(0).valorBruto());
       assertEquals(new BigDecimal("99.00"), parcelaDtoList.get(0).valorLiquido());
       assertEquals(new BigDecimal("1"), parcelaDtoList.get(0).valorDesconto());
    }

    @Test
    @DisplayName("Test Calcular Parcelas Credito a Vista")
    public void testCalcularParcelasCreditoAvista() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Transacao transacao = new Transacao();
        transacao.setCodigoLoja("LOJA123");
        transacao.setCodigoPedido("PEDIDO456");
        transacao.setData(formatter.parse("07/05/2025"));
        transacao.setQuantidadeParcelas(1);
        transacao.setValor(new BigDecimal("100.00"));
        transacao.setTaxa(new BigDecimal("1"));
        transacao. setTipoTransacao("CREDITO");
        transacao. setStatus(StatusTransacao.PENDENTE);
        transacao.setCartao("123456*******789");
        transacao.setCodigoAutorizacao("AUTH123456");
        transacao.setNsu(987654);
        transacao.setBandeira("VISA");
        transacao.setParcelas(new ArrayList<>());

        Date dataPagamento = formatter.parse("18/06/2025");

        given(diaUtil.diasUteis(any(), anyInt())).willReturn(dataPagamento);

        //When
        List<ParcelaDto> parcelaDtoList = calcularParcelas.calcular(transacao);

        //Then
        assertFalse(parcelaDtoList.isEmpty());
        assertEquals(dataPagamento, parcelaDtoList.get(0).data());
        assertEquals(new BigDecimal("100.00"), parcelaDtoList.get(0).valorBruto());
        assertEquals(new BigDecimal("99.00"), parcelaDtoList.get(0).valorLiquido());
        assertEquals(new BigDecimal("1"), parcelaDtoList.get(0).valorDesconto());
    }

    @Test
    @DisplayName("Test Calcular Parcelas Credito")
    public void testCalcularParcelasCredito() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Transacao transacao = new Transacao();
        transacao.setCodigoLoja("LOJA123");
        transacao.setCodigoPedido("PEDIDO456");
        transacao.setData(formatter.parse("07/05/2025"));
        transacao.setQuantidadeParcelas(2);
        transacao.setValor(new BigDecimal("100.00"));
        transacao.setTaxa(new BigDecimal("1"));
        transacao. setTipoTransacao("CREDITO");
        transacao. setStatus(StatusTransacao.PENDENTE);
        transacao.setCartao("123456*******789");
        transacao.setCodigoAutorizacao("AUTH123456");
        transacao.setNsu(987654);
        transacao.setBandeira("VISA");
        transacao.setParcelas(new ArrayList<>());

        Date dataPagamento = formatter.parse("18/06/2025");

        given(diaUtil.diasUteis(any(), anyInt())).willReturn(dataPagamento);

        //When
        List<ParcelaDto> parcelaDtoList = calcularParcelas.calcular(transacao);

        //Then
        assertFalse(parcelaDtoList.isEmpty());
        assertEquals(dataPagamento, parcelaDtoList.get(0).data());
        assertEquals(new BigDecimal("50.00"), parcelaDtoList.get(0).valorBruto());
        assertEquals(new BigDecimal("49.50"), parcelaDtoList.get(0).valorLiquido());
        assertEquals(new BigDecimal("0.50"), parcelaDtoList.get(0).valorDesconto());

        assertEquals(dataPagamento, parcelaDtoList.get(1).data());
        assertEquals(new BigDecimal("50.00"), parcelaDtoList.get(1).valorBruto());
        assertEquals(new BigDecimal("49.50"), parcelaDtoList.get(1).valorLiquido());
        assertEquals(new BigDecimal("0.50"), parcelaDtoList.get(1).valorDesconto());
    }

    @Test
    @DisplayName("Test Calcula Data Pagamento Parcela Debito")
    public void testCalculaDataPagamentoParcelaDebito() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Transacao transacao = new Transacao();
        transacao.setCodigoLoja("LOJA123");
        transacao.setCodigoPedido("PEDIDO456");
        transacao.setData(formatter.parse("07/05/2025"));
        transacao.setQuantidadeParcelas(1);
        transacao.setValor(new BigDecimal("100.00"));
        transacao.setTaxa(new BigDecimal("1"));
        transacao. setTipoTransacao("DEBITO");
        transacao. setStatus(StatusTransacao.PENDENTE);
        transacao.setCartao("123456*******789");
        transacao.setCodigoAutorizacao("AUTH123456");
        transacao.setNsu(987654);
        transacao.setBandeira("VISA");
        transacao.setParcelas(new ArrayList<>());

        Date dataPagamento = formatter.parse("08/05/2025");

        given(diaUtil.diasUteis(any(), anyInt())).willReturn(dataPagamento);

        //When

        Date dataCalculada = calcularParcelas.dataPagamentoParcela(transacao, transacao.getQuantidadeParcelas());

        //Then
        assertNotNull(dataCalculada);
        assertEquals(dataCalculada, dataPagamento);
    }

    @Test
    @DisplayName("Test Calcula Data Pagamento Parcela Credito Avista")
    public void testCalculaDataPagamentoParcelaCreditoAvista() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Transacao transacao = new Transacao();
        transacao.setCodigoLoja("LOJA123");
        transacao.setCodigoPedido("PEDIDO456");
        transacao.setData(formatter.parse("07/05/2025"));
        transacao.setQuantidadeParcelas(1);
        transacao.setValor(new BigDecimal("100.00"));
        transacao.setTaxa(new BigDecimal("1"));
        transacao. setTipoTransacao("DEBITO");
        transacao. setStatus(StatusTransacao.PENDENTE);
        transacao.setCartao("123456*******789");
        transacao.setCodigoAutorizacao("AUTH123456");
        transacao.setNsu(987654);
        transacao.setBandeira("VISA");
        transacao.setParcelas(new ArrayList<>());

        Date dataPagamento = formatter.parse("18/06/2025");

        given(diaUtil.diasUteis(any(), anyInt())).willReturn(dataPagamento);

        //When

        Date dataCalculada = calcularParcelas.dataPagamentoParcela(transacao, transacao.getQuantidadeParcelas());

        //Then
        assertNotNull(dataCalculada);
        assertEquals(dataCalculada, dataPagamento);
    }


    @Test
    @DisplayName("test calcula data pagamento parcela credito")
    public void testCalculaDataPagamentoParcelaCredito() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Transacao transacao = new Transacao();
        transacao.setCodigoLoja("LOJA123");
        transacao.setCodigoPedido("PEDIDO456");
        transacao.setData(formatter.parse("07/05/2025"));
        transacao.setQuantidadeParcelas(2);
        transacao.setValor(new BigDecimal("100.00"));
        transacao.setTaxa(new BigDecimal("1"));
        transacao. setTipoTransacao("CREDITO");
        transacao. setStatus(StatusTransacao.PENDENTE);
        transacao.setCartao("123456*******789");
        transacao.setCodigoAutorizacao("AUTH123456");
        transacao.setNsu(987654);
        transacao.setBandeira("VISA");
        transacao.setParcelas(new ArrayList<>());

        Date dataPagamentoPrimeiraParcela = formatter.parse("18/06/2025");
        given(diaUtil.diasUteis(any(), anyInt())).willReturn(dataPagamentoPrimeiraParcela);
        Date dataCalculadaPagamentoPrimeiraParcela = calcularParcelas.dataPagamentoParcela(transacao, 1);

        Date dataPagamentoSegundaParcela = formatter.parse("30/07/2025");
        given(diaUtil.diasUteis(any(), anyInt())).willReturn(dataPagamentoSegundaParcela);
        Date dataCalculadaSegundaPrimeiraParcela = calcularParcelas.dataPagamentoParcela(transacao, 2);

        //Then
        assertNotNull(dataCalculadaPagamentoPrimeiraParcela);
        assertEquals(dataCalculadaPagamentoPrimeiraParcela, dataPagamentoPrimeiraParcela);

        assertNotNull(dataCalculadaSegundaPrimeiraParcela);
        assertEquals(dataCalculadaSegundaPrimeiraParcela, dataPagamentoSegundaParcela);
    }




}