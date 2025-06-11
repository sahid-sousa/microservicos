package br.com.transacao.application.services.transacao;

import br.com.commons.dto.transacao.ParcelaDto;
import br.com.commons.dto.transacao.TransacaoDto;
import br.com.transacao.adpaters.gateway.parcela.ParcelaGateway;
import br.com.transacao.adpaters.gateway.transacao.TransacaoGateway;
import br.com.transacao.application.usecases.parcela.CalcularParcelas;
import br.com.transacao.application.usecases.parcela.CriarParcela;
import br.com.transacao.domain.entities.Parcela;
import br.com.transacao.domain.entities.StatusTransacao;
import br.com.transacao.domain.entities.Transacao;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CriarTransacaoImplTest {

    @Spy
    @InjectMocks
    CriarTransacaoImpl criarTransacao;

    @Mock
    TransacaoGateway transacaoGateway;

    @Mock
    ParcelaGateway parcelaGateway;

    @Mock
    CalcularParcelas calcularParcelas;

    @Mock
    CriarParcela criarParcela;

    @Test
    @DisplayName("Test Execute Creation Transaction Type Debit")
    public void testExecuteCreationTransactionTypeDebit() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        TransacaoDto transacaoDto = new TransacaoDto(
                "LOJA123", "PEDIDO456","uuid-abc-123",
                formatter.parse("07/05/2025"),1, new BigDecimal("100.00"),
                new BigDecimal("1"), "CREDITO","123456******7890",
                "AUTH123456",987654,"VISA"
        );

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

        ParcelaDto parcelaDto = new ParcelaDto(
                formatter.parse("18/06/2025"),
                1, new BigDecimal("100.00"),
                new BigDecimal("99.00"), new BigDecimal("1")
        );

        Parcela parcela = new Parcela();
        parcela.setData(formatter.parse("18/06/2025"));
        parcela.setNumero(1);
        parcela.setValorBruto(new BigDecimal("100.00"));
        parcela.setValorLiquido(new BigDecimal("99.00"));
        parcela.setValorDesconto(new BigDecimal("1"));

        given(transacaoGateway.findByAtributos(
                any(),
                anyInt(),
                any(),
                anyString(),
                anyString(),
                anyString(),
                anyInt(),
                anyString(),
                any()
        )).willReturn(Optional.of(transacao));

        given(calcularParcelas.calcular(transacao)).willReturn(List.of(parcelaDto));
        given(criarParcela.criar(any(), any())).willReturn(parcela);

        //When
        criarTransacao.executar(transacaoDto);

        //Then
        verify(transacaoGateway, times(1)).save(any(Transacao.class));
        verify(parcelaGateway, times(1)).save(any(Parcela.class));
    }

    @Test
    @DisplayName("Test Execute Creation Transaction Type Credit Avista")
    public void testExecuteCreationTransactionTypeCreditAvista() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        TransacaoDto transacaoDto = new TransacaoDto(
                "LOJA123", "PEDIDO456","uuid-abc-123",
                formatter.parse("07/05/2025"),1, new BigDecimal("100.00"),
                new BigDecimal("1"), "CREDITO","123456******7890",
                "AUTH123456",987654,"VISA"
        );

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

        ParcelaDto parcelaDto = new ParcelaDto(
                formatter.parse("18/06/2025"),
                1, new BigDecimal("100.00"),
                new BigDecimal("99.00"), new BigDecimal("1")
        );

        Parcela parcela = new Parcela();
        parcela.setData(formatter.parse("18/06/2025"));
        parcela.setNumero(1);
        parcela.setValorBruto(new BigDecimal("100.00"));
        parcela.setValorLiquido(new BigDecimal("99.00"));
        parcela.setValorDesconto(new BigDecimal("1"));

        given(transacaoGateway.findByAtributos(
                any(),
                anyInt(),
                any(),
                anyString(),
                anyString(),
                anyString(),
                anyInt(),
                anyString(),
                any()
        )).willReturn(Optional.of(transacao));
        given(calcularParcelas.calcular(transacao)).willReturn(List.of(parcelaDto));
        given(criarParcela.criar(any(), any())).willReturn(parcela);

        //When
        criarTransacao.executar(transacaoDto);

        //Then
        verify(transacaoGateway, times(1)).save(any(Transacao.class));
        verify(parcelaGateway, times(1)).save(any(Parcela.class));
    }

    @Test
    @DisplayName("Test Execute Creation Transaction Type Credit")
    public void testExecuteCreationTransactionTypeCredit() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        TransacaoDto transacaoDto = new TransacaoDto(
                "LOJA123", "PEDIDO456","uuid-abc-123",
                formatter.parse("07/05/2025"),1, new BigDecimal("100.00"),
                new BigDecimal("2"), "CREDITO","123456******7890",
                "AUTH123456",987654,"VISA"
        );

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

        ParcelaDto parcelaUmDto = new ParcelaDto(
                formatter.parse("18/06/2025"),1, new BigDecimal("100.00"),
                new BigDecimal("49.50"), new BigDecimal("0.50")
        );

        ParcelaDto parcelaDoisDto = new ParcelaDto(
                formatter.parse("18/07/2025"),1, new BigDecimal("100.00"),
                new BigDecimal("49.50"), new BigDecimal("0.50")
        );

        Parcela parcelaUm = new Parcela();
        parcelaUm.setData(formatter.parse("18/06/2025"));
        parcelaUm.setNumero(1);
        parcelaUm.setValorBruto(new BigDecimal("100.00"));
        parcelaUm.setValorLiquido(new BigDecimal("49.50"));
        parcelaUm.setValorDesconto(new BigDecimal("0.50"));

        Parcela parcelaDois = new Parcela();
        parcelaUm.setData(formatter.parse("18/06/2025"));
        parcelaUm.setNumero(1);
        parcelaUm.setValorBruto(new BigDecimal("100.00"));
        parcelaUm.setValorLiquido(new BigDecimal("49.50"));
        parcelaUm.setValorDesconto(new BigDecimal("0.50"));

        given(transacaoGateway.findByAtributos(
                any(),
                anyInt(),
                any(),
                anyString(),
                anyString(),
                anyString(),
                anyInt(),
                anyString(),
                any()
        )).willReturn(Optional.of(transacao));
        given(calcularParcelas.calcular(transacao)).willReturn(List.of(parcelaUmDto, parcelaDoisDto));
        given(criarParcela.criar(any(), any())).willReturn(parcelaUm);
        given(criarParcela.criar(any(), any())).willReturn(parcelaDois);

        //When
        criarTransacao.executar(transacaoDto);

        //Then
        verify(transacaoGateway, times(1)).save(any(Transacao.class));
        verify(parcelaGateway, times(2)).save(any(Parcela.class));
    }

    @Test
    @DisplayName("Test Execute Creation Transaction with parcelas")
    public void testExecuteCreationTransactionWithParcelas() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        TransacaoDto transacaoDto = new TransacaoDto(
                "LOJA123", "PEDIDO456", "uuid-abc-123",
                formatter.parse("07/05/2025"), 1, new BigDecimal("100.00"),
                new BigDecimal("2"), "CREDITO", "123456******7890",
                "AUTH123456", 987654, "VISA"
        );

        Transacao transacao = new Transacao();
        transacao.setCodigoLoja("LOJA123");
        transacao.setCodigoPedido("PEDIDO456");
        transacao.setData(formatter.parse("07/05/2025"));
        transacao.setQuantidadeParcelas(2);
        transacao.setValor(new BigDecimal("100.00"));
        transacao.setTaxa(new BigDecimal("1"));
        transacao.setTipoTransacao("CREDITO");
        transacao.setStatus(StatusTransacao.PENDENTE);
        transacao.setCartao("123456*******789");
        transacao.setCodigoAutorizacao("AUTH123456");
        transacao.setNsu(987654);
        transacao.setBandeira("VISA");

        Parcela parcelaUm = new Parcela();
        parcelaUm.setData(formatter.parse("18/06/2025"));
        parcelaUm.setNumero(1);
        parcelaUm.setValorBruto(new BigDecimal("100.00"));
        parcelaUm.setValorLiquido(new BigDecimal("49.50"));
        parcelaUm.setValorDesconto(new BigDecimal("0.50"));

        transacao.adicionarParcela(parcelaUm);

        Parcela parcelaDois = new Parcela();
        parcelaUm.setData(formatter.parse("18/06/2025"));
        parcelaUm.setNumero(1);
        parcelaUm.setValorBruto(new BigDecimal("100.00"));
        parcelaUm.setValorLiquido(new BigDecimal("49.50"));
        parcelaUm.setValorDesconto(new BigDecimal("0.50"));

        transacao.adicionarParcela(parcelaDois);

        given(transacaoGateway.findByAtributos(
                any(),
                anyInt(),
                any(),
                anyString(),
                anyString(),
                anyString(),
                anyInt(),
                anyString(),
                any()
        )).willReturn(Optional.of(transacao));

        //When
        criarTransacao.executar(transacaoDto);

        //Then
        verify(transacaoGateway, times(0)).save(any(Transacao.class));
        verify(parcelaGateway, times(0)).save(any(Parcela.class));
    }

    @Test
    @DisplayName("Test create Transacao")
    public void testDeveCriarTransacao() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        TransacaoDto transacaoDto = new TransacaoDto(
                "LOJA123", "PEDIDO456", "uuid-abc-123",
                formatter.parse("07/05/2025"), 1, new BigDecimal("100.00"),
                new BigDecimal("2"), "CREDITO", "123456******7890",
                "AUTH123456", 987654, "VISA"
        );

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
        given(transacaoGateway.findByAtributos(
                any(),
                anyInt(),
                any(),
                anyString(),
                anyString(),
                anyString(),
                anyInt(),
                anyString(),
                any()
        )).willReturn(Optional.of(transacao));

        //When
        Transacao entity = criarTransacao.criarTransacao(transacaoDto);

        //Then
        assertNotNull(entity);
        assertEquals("LOJA123", entity.getCodigoLoja());
        assertEquals("PEDIDO456", entity.getCodigoPedido());
        assertEquals(2, entity.getQuantidadeParcelas());
    }


    @Test
    @DisplayName("Test create Transacao if no Exsists")
    public void testDeveCriarTransacaoIfNotExists() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        TransacaoDto transacaoDto = new TransacaoDto(
                "LOJA123", "PEDIDO456", "uuid-abc-123",
                formatter.parse("07/05/2025"), 1, new BigDecimal("100.00"),
                new BigDecimal("1"), "CREDITO", "123456******7890",
                "AUTH123456", 987654, "VISA"
        );

        given(transacaoGateway.findByAtributos(
                any(),
                anyInt(),
                any(),
                anyString(),
                anyString(),
                anyString(),
                anyInt(),
                anyString(),
                any()
        )).willReturn(Optional.empty());

        //When
        Transacao entity = criarTransacao.criarTransacao(transacaoDto);

        //Then
        assertNotNull(entity);
        assertEquals("LOJA123", entity.getCodigoLoja());
        assertEquals("PEDIDO456", entity.getCodigoPedido());
        assertEquals(1, entity.getQuantidadeParcelas());
    }

}
