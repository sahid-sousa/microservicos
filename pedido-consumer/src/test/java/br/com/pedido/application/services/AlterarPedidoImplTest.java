package br.com.pedido.application.services;

import br.com.commons.dto.pedido.LojaDto;
import br.com.commons.dto.pedido.PagamentoDto;
import br.com.commons.dto.pedido.PedidoDto;
import br.com.pedido.adapters.gateway.pedido.PedidoGateway;
import br.com.pedido.domain.entities.Loja;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AlterarPedidoImplTest {

    @Spy
    @InjectMocks
    AlterarPedidoImpl alterarPedido;

    @Mock
    PedidoGateway pedidoGateway;

    private Loja loja;
    private Pedido pedido;

    @BeforeEach
    void setup() {
        //Given
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
    @DisplayName("Test Alterar Pedido when execute method find Pedido Conciliado")
    void testAlterarPedido_whenExecuteMethodFindPedidoConciliado() {
        //Given
        LojaDto lojaDto = new LojaDto("loja-uuid-001","LOJA001", "12.345.678/0001-99");
        PagamentoDto pagamentoDto = new PagamentoDto( "pagamento-uuid-001", "CARTAO", "CREDITO", 3, "1234********5678", "AUTH123", 987654, "VISA", 150.00 );
        PedidoDto pedidoDto = new PedidoDto( "pedido-uuid-001", "PED123456", 150.00, new Date(), false, true, lojaDto, List.of(pagamentoDto) );

        given(pedidoGateway.findByUuid(anyString())).willReturn(Optional.of(pedido));

        //When

        alterarPedido.executar(pedidoDto);

        //Then
        verify(pedidoGateway, times(1)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Test Alterar Pedido when execute method find Pedido not Conciliado")
    void testAlterarPedido_whenExecuteMethodFindPedidoNotConciliado() {
        //Given
        LojaDto lojaDto = new LojaDto("loja-uuid-001","LOJA001", "12.345.678/0001-99");
        PagamentoDto pagamentoDto = new PagamentoDto( "pagamento-uuid-001", "CARTAO", "CREDITO", 3, "1234********5678", "AUTH123", 987654, "VISA", 150.00 );
        PedidoDto pedidoDto = new PedidoDto( "pedido-uuid-001", "PED123456", 150.00, new Date(), false, false, lojaDto, List.of(pagamentoDto) );


        given(pedidoGateway.findByUuid(anyString())).willReturn(Optional.of(pedido));

        //When
        alterarPedido.executar(pedidoDto);

        //Then
        verify(pedidoGateway, times(0)).save(any(Pedido.class));
    }

    @Test
    @DisplayName("Test Alterar Pedido when execute Method not find Pedido")
    void testAlterarPedido_whenExecuteMethodNotFindPedido() {
        //Given
        LojaDto lojaDto = new LojaDto("loja-uuid-001","LOJA001", "12.345.678/0001-99");
        PagamentoDto pagamentoDto = new PagamentoDto( "pagamento-uuid-001", "CARTAO", "CREDITO", 3, "1234********5678", "AUTH123", 987654, "VISA", 150.00 );
        PedidoDto pedidoDto = new PedidoDto( "pedido-uuid-001", "PED123456", 150.00, new Date(), false, false, lojaDto, List.of(pagamentoDto) );


        given(pedidoGateway.findByUuid(anyString())).willReturn(Optional.empty());

        //When
        alterarPedido.executar(pedidoDto);

        //Then
        verify(pedidoGateway, times(0)).save(any(Pedido.class));
    }
}