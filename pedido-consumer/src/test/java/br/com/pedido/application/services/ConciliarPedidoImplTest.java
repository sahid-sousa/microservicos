package br.com.pedido.application.services;

import br.com.commons.dto.pedido.LojaDto;
import br.com.commons.dto.pedido.PagamentoDto;
import br.com.commons.dto.pedido.PedidoDto;
import br.com.pedido.adapters.gateway.pagamento.PagamentoGateway;
import br.com.pedido.adapters.gateway.pedido.PedidoGateway;
import br.com.pedido.domain.entities.Loja;
import br.com.pedido.domain.entities.Pagamento;
import br.com.pedido.domain.entities.Pedido;
import br.com.pedido.infrastructure.broker.RpcProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ConciliarPedidoImplTest {

    @Spy
    @InjectMocks
    ConciliarPedidoImpl conciliarPedido;

    @Mock
    RpcProducer<PedidoDto> producer;

    @Mock
    PedidoGateway pedidoGateway;

    @Mock
    PagamentoGateway pagamentoGateway;

    private PedidoDto pedidoDto;
    private PagamentoDto pagamentoDto;
    private Pagamento pagamento;
    private Loja loja;
    private Pedido pedido;

    @BeforeEach
    void setup() {
        //Given
        LojaDto lojaDto = new LojaDto("loja-uuid-001","LOJA001", "12.345.678/0001-99");
        pagamentoDto = new PagamentoDto(
                "pagamento-uuid-001",
                "CARTAO",
                "CREDITO",
                3,
                "1234********5678",
                "AUTH123",
                987654,
                "VISA",
                150.00
        );
        pedidoDto = new PedidoDto(
                "pedido-uuid-001",
                "PED123456",
                150.00,
                new Date(),
                false,
                false,
                lojaDto,
                List.of(pagamentoDto)
        );

        loja = new Loja();
        loja.setCodigo("LOJA-001");
        loja.setCnpj("12345678000199");

        pagamento = new Pagamento();
        pagamento.setTipo("CARTAO");
        pagamento.setTipoTransacao("CREDITO");
        pagamento.setParcelas(3);
        pagamento.setCartao("1234********5678");
        pagamento.setCodigoAutorizacao("AUTH123");
        pagamento.setNsu(987654);
        pagamento.setBandeira("VISA");
        pagamento.setValor(150.00);

        pedido = new Pedido();
        pedido.setCodigo("PED123456");
        pedido.setValor(150.00);
        pedido.setData(new Date());
        pedido.setFaturado(true);
        pedido.setConciliado(false);

        loja.adicionarPedido(pedido);
        pedido.adicionarPagamento(pagamento);
    }

    @Test
    @DisplayName("Test given execute conciliar Pedido")
    void testGivenExecuteConciliarPedido() {
        //Given
        given(pedidoGateway.findAllStatus(anyBoolean(), anyBoolean(), any())).willReturn(List.of(1L));
        given(pedidoGateway.findById(anyLong())).willReturn(pedido);

        //Then
        conciliarPedido.executar();

        //When
        verify(pedidoGateway, times(1)).findById(anyLong());
        verify(conciliarPedido, times(1)).convertFrom(any(Pedido.class));
        verify(producer, times(1)).enviar(any(PedidoDto.class));
    }

    @Test
    @DisplayName("Test given PagamentoDto convert from Pedido")
    void testGivenPagamentoDtoConvertFromPedido() {
        //Given
        given(pagamentoGateway.findPagamentoByPedido(pedido)).willReturn(List.of(pagamento));

        //When
        PedidoDto pedidoDto = conciliarPedido.convertFrom(pedido);

        //Then
        assertNotNull(pedidoDto);
        assertEquals("PED123456", pedidoDto.codigo());
        assertEquals(150.00, pedidoDto.valor());
        assertTrue(pedidoDto.faturado());
        assertFalse(pedidoDto.conciliado());
    }

    @Test
    @DisplayName("Test given PagamentoDto List from Pedido")
    void testGivenPagamentoDtoList_fromPedido() {
        //Given
        given(pagamentoGateway.findPagamentoByPedido(pedido)).willReturn(List.of(pagamento));

        //When
        List<PagamentoDto> pagamentosDto = conciliarPedido.getPagamentoDtos(pedido);

        //Then
        assertFalse(pagamentosDto.isEmpty());
        assertEquals("CARTAO", pagamentosDto.get(0).tipo());
        assertEquals("CREDITO", pagamentosDto.get(0).tipoTransacao());
        assertEquals(3, pagamentosDto.get(0).parcelas());
    }

    @Test
    @DisplayName("Test given conciliar PedidoObject when send PedidoDto ")
    void testGivenConciliarPedidoObject_whenSendPedidoDto() {
        //When
        conciliarPedido.conciliar(pedidoDto);

        //Then
        verify(producer, times(1)).enviar(any(PedidoDto.class));
    }

}