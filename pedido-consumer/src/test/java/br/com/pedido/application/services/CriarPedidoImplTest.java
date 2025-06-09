package br.com.pedido.application.services;

import br.com.commons.dto.pedido.LojaDto;
import br.com.commons.dto.pedido.PagamentoDto;
import br.com.commons.dto.pedido.PedidoDto;
import br.com.pedido.adapters.gateway.loja.LojaGateway;
import br.com.pedido.adapters.gateway.pagamento.PagamentoGateway;
import br.com.pedido.adapters.gateway.pedido.PedidoGateway;
import br.com.pedido.domain.entities.Loja;
import br.com.pedido.domain.entities.Pagamento;
import br.com.pedido.domain.entities.Pedido;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarPedidoImplTest {

    @Spy
    @InjectMocks
    CriarPedidoImpl criarPedidoImpl;

    @Mock
    PedidoGateway pedidoGateway;

    @Mock
    LojaGateway lojaGateway;

    @Mock
    PagamentoGateway pagamentoGateway;

    private PedidoDto pedidoDto;
    private PagamentoDto pagamentoDto;
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

        pedido = new Pedido();
        pedido.setUuid(UUID.randomUUID().toString());
        pedido.setCodigo("PED123456");
        pedido.setValor(150.00);
        pedido.setData(new Date());
        pedido.setFaturado(true);
        pedido.setConciliado(false);

        loja.adicionarPedido(pedido);
    }

    @Test
    @DisplayName("Test given execute create PedidoObject when create Pedido")
    void testGivenExecutePedidoObject_whenCreatePedido() {
        //Given
        doReturn(pedido).when(criarPedidoImpl).criarPedido(pedidoDto);

        //When
        criarPedidoImpl.executar(pedidoDto);

        //Then
        verify(criarPedidoImpl, times(1)).criarLoja(any(), any());
        verify(criarPedidoImpl, times(1)).criarPagamento(any(), any());
        //verify(pedidoGateway, times(1)).save(eq(pedido));
    }

    @Test
    @DisplayName("Test given execute create when exists PedidoObject when create Pedido")
    void testGivenExecuteCreateWhenExistsPedidoObject_whenCreatePedido() {
        //Given
        pedido.setId(1L);
        doReturn(pedido).when(criarPedidoImpl).criarPedido(pedidoDto);

        //When
        criarPedidoImpl.executar(pedidoDto);

        //Then
        //verify(criarPedidoImpl, times(1)).criarLoja(any(), any());
        //verify(criarPedidoImpl, times(1)).criarPagamento(any(), any());
        verify(pedidoGateway, times(1)).save(eq(pedido));
    }

    @Test
    @DisplayName("Test given PedidoObject when create Pedido then return created Pedido")
    void testGivenPedidoObject_whenCreatePedido_thenReturnCreatedPedido() {
        //Given
        given(pedidoGateway.findByCodigo(anyString())).willReturn(Optional.empty());

        //When
        Pedido pedido = criarPedidoImpl.criarPedido(pedidoDto);

        //Then
        assertNotNull(pedido);
        assertEquals(pedidoDto.codigo(), pedido.getCodigo());
        assertEquals(pedidoDto.valor(), pedido.getValor());
        assertFalse(pedido.getFaturado());
    }

    @Test
    @DisplayName("Test given LojaObject when create Pedido then return created Loja")
    void testGivenLojaObject_whenCreatePedido_thenReturnCreatedLoja() {
        //Given
        given(lojaGateway.findByCnpj(anyString())).willReturn(Optional.empty());

        //When
        Pedido pedido = criarPedidoImpl.criarPedido(pedidoDto);
        criarPedidoImpl.criarLoja(pedidoDto, pedido);

        //Then
        verify(lojaGateway, times(1)).save(any(Loja.class));

    }

    @Test
    @DisplayName("Test given PagamentoObject when create Pedido then return created Pagamento")
    void testGivenPagamentoObject_whenCreatePedido_thenReturnCreatedPagamento() {
        //Given
        given(pagamentoGateway.findByCartaoAndBandeiraAndNsuAndCodigoAutorizacaoAndParcelas(
                anyString(),
                anyString(),
                anyInt(),
                anyString(),
                anyInt()
        )).willReturn(Optional.empty());

        //When
        Pedido pedido = criarPedidoImpl.criarPedido(pedidoDto);
        criarPedidoImpl.criarPagamento(pedidoDto, pedido);

        //Then
        verify(pagamentoGateway, times(1)).save(any(Pagamento.class));

    }

    @Test
    @DisplayName("Test given new PagamentoObject when create Pedido then return created Pagamento")
    void testGivenNewPagamentoObject_whenCreatePedido_thenReturnCreatedPagamento() {
        //Given
        given(pedidoGateway.findByCodigo(anyString())).willReturn(Optional.empty());

        //When
        Pedido pedido = criarPedidoImpl.criarPedido(pedidoDto);
        Pagamento pagamento = criarPedidoImpl.getNovoPagamento(pedido, pagamentoDto);

        //Then
        assertNotNull(pagamento);
        assertEquals("CARTAO", pagamento.getTipo());
        assertEquals("CREDITO", pagamento.getTipoTransacao());
        assertEquals(3, pagamento.getParcelas());
        assertEquals("1234********5678", pagamento.getCartao());
        assertEquals("VISA", pagamento.getBandeira());

    }

}