package br.com.pedido.adapters.gateway.pedido;

import br.com.pedido.adapters.gateway.loja.LojaGateway;
import br.com.pedido.domain.entities.Loja;
import br.com.pedido.domain.entities.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PedidoGatewayTest {

    @Mock
    PedidoGateway pedidoGateway;

    @Mock
    LojaGateway lojaGateway;

    private Loja loja;
    private Pedido pedido;

    @BeforeEach
    public void setup() {
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
    @DisplayName("Test given PedidoObject when save Pedido then return saved Pedido")
    void testGivenPedidoObject_whenSavePedido_thenReturnSavedPedido() {
        //When

        given(lojaGateway.save(loja)).willReturn(loja);
        given(pedidoGateway.save(pedido)).willReturn(pedido);

        lojaGateway.save(loja);
        Pedido pedidoEntity = pedidoGateway.save(pedido);

        //Then
        assertNotNull(pedidoEntity);
        assertEquals("PED123456", pedidoEntity.getCodigo());
        assertEquals(150.00, pedidoEntity.getValor());
        assertTrue(pedidoEntity.getFaturado());
        assertFalse(pedidoEntity.getConciliado());
    }

    @Test
    @DisplayName("Test given PedidoObject when findById then return Pedido Object")
    void testGivenPedidoObject_whenFindById_thenReturnPagamentoObject() {
        //Given
        given(pedidoGateway.findById(anyLong())).willReturn(pedido);

        //When
        Pedido findedPedidoEntity = pedidoGateway.findById(1L);

        //Then
        assertNotNull(findedPedidoEntity);
        assertEquals("PED123456", findedPedidoEntity.getCodigo());
        assertEquals(150.00, findedPedidoEntity.getValor());
        assertTrue(findedPedidoEntity.getFaturado());
        assertFalse(findedPedidoEntity.getConciliado());
    }

    @Test
    @DisplayName("Test given PedidoObject when findAllByLoja then return PedidoList")
    void testGivenPedidoObject_whenFindAllByLoja_thenReturnPedidoList() {
        //When
        given(pedidoGateway.findAllByLoja(loja)).willReturn(List.of(pedido));

        //When
        List<Pedido> findedPedidoEntityList = pedidoGateway.findAllByLoja(loja);

        //Then
        assertFalse(findedPedidoEntityList.isEmpty());
        assertEquals("PED123456", findedPedidoEntityList.get(0).getCodigo());
        assertEquals(150.00, findedPedidoEntityList.get(0).getValor());
        assertTrue(findedPedidoEntityList.get(0).getFaturado());
        assertFalse(findedPedidoEntityList.get(0).getConciliado());
    }

    @Test
    @DisplayName("Test given Pedido object when findByCodigo' then return Pedido object")
    void testGivenPedidoObject_whenFindByCodigo_thenReturnPedidoObject() {
        //Given
        given(pedidoGateway.findByCodigo(anyString())).willReturn(Optional.of(pedido));

        //When
        Optional<Pedido> findedPedidoEntity = pedidoGateway.findByCodigo(anyString());

        //Then
        assertTrue(findedPedidoEntity.isPresent());
        assertEquals("PED123456", findedPedidoEntity.get().getCodigo());
        assertEquals(150.00, findedPedidoEntity.get().getValor());
        assertTrue(findedPedidoEntity.get().getFaturado());
        assertFalse(findedPedidoEntity.get().getConciliado());
    }

    @Test
    @DisplayName("Test given Pedido object when findByUuid then return Pedido object")
    void testGivenPedidoObject_whenFindByUuid_thenReturnPedidoObject() {
        //Given
        given(pedidoGateway.findByUuid(anyString())).willReturn(Optional.of(pedido));

        //When
        Optional<Pedido> findedPedidoOptionalEntity = pedidoGateway.findByUuid(anyString());

        //Then
        assertTrue(findedPedidoOptionalEntity.isPresent());
        assertEquals("PED123456", findedPedidoOptionalEntity.get().getCodigo());
        assertEquals(150.00, findedPedidoOptionalEntity.get().getValor());
        assertTrue(findedPedidoOptionalEntity.get().getFaturado());
        assertFalse(findedPedidoOptionalEntity.get().getConciliado());
    }


    @Test
    @DisplayName("Test given PedidoObject when findAllByLoja then return PedidoList")
    void testGivenPedidoIdListObject_whenFindAllStatus_thenReturnPedidoList() {
        //Given
        given(pedidoGateway.findAllStatus(anyBoolean(), anyBoolean(), any())).willReturn(List.of(1L, 2L, 3L));

        //When
        List<Long> findedLongIdList = pedidoGateway.findAllStatus(anyBoolean(), anyBoolean(), any());

        //Then
        assertFalse(findedLongIdList.isEmpty());
    }

}