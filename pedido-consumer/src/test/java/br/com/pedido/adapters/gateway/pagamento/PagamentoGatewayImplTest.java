package br.com.pedido.adapters.gateway.pagamento;

import br.com.pedido.domain.entities.Loja;
import br.com.pedido.domain.entities.Pagamento;
import br.com.pedido.domain.entities.Pedido;
import br.com.pedido.infrastructure.database.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PagamentoGatewayImplTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @InjectMocks
    PagamentoGatewayImpl pagamentoGateway;

    private Pagamento pagamento;
    private Pedido pedido;

    @BeforeEach
    public void setup() {
        //Given
        Loja loja = new Loja();
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
    @DisplayName("Test given PagamentoObject when save Pagamento then return saved Pagamento")
    void testGivenPagamentoObject_whenSavePagamento_thenReturnSavedPagamento() {
        //Given
        given(pagamentoRepository.save(pagamento)).willReturn(pagamento);

        //When
        Pagamento pagamentoEntity = pagamentoGateway.save(pagamento);

        //Then
        assertNotNull(pagamentoEntity);
        assertEquals("CARTAO", pagamento.getTipo());
        assertEquals("CREDITO", pagamento.getTipoTransacao());
        assertEquals(3, pagamento.getParcelas());
        assertEquals("1234********5678", pagamento.getCartao());
        assertEquals("AUTH123", pagamento.getCodigoAutorizacao());
        assertEquals(987654, pagamento.getNsu());
        assertEquals("VISA", pagamento.getBandeira());
        assertEquals(150.00, pagamento.getValor());
    }

    @Test
    @DisplayName("Test given PagamentoObject when findById then return PagamentoObject")
    void testGivenPagamentoObject_whenFindById_thenReturnPagamento() {
        //Given
        given(pagamentoRepository.findById(anyLong())).willReturn(pagamento);

        //When
        Pagamento findedPagamentoEntity = pagamentoGateway.findById(anyLong());

        //Then
        assertNotNull(findedPagamentoEntity);
        assertEquals("CARTAO", findedPagamentoEntity.getTipo());
        assertEquals("CREDITO", findedPagamentoEntity.getTipoTransacao());
        assertEquals(3, findedPagamentoEntity.getParcelas());
        assertEquals("1234********5678", findedPagamentoEntity.getCartao());
        assertEquals("AUTH123", findedPagamentoEntity.getCodigoAutorizacao());
        assertEquals(987654, findedPagamentoEntity.getNsu());
        assertEquals("VISA", findedPagamentoEntity.getBandeira());
        assertEquals(150.00, findedPagamentoEntity.getValor());
    }

    @Test
    @DisplayName("Test given PagamentoListObject when findPagamentoByPedido then return PagamentoList")
    void testGivenPagamentoListObject_whenFindPagamentoByPedido_thenReturnPagamentoList() {
        //Given
        given(pagamentoRepository.findPagamentoByPedido(pedido)).willReturn(List.of(pagamento));

        //When
        List<Pagamento> findedPagamentoEntityList = pagamentoGateway.findPagamentoByPedido(pedido);

        //Then
        assertFalse(findedPagamentoEntityList.isEmpty());
        assertEquals("CARTAO", findedPagamentoEntityList.get(0).getTipo());
        assertEquals("CREDITO", findedPagamentoEntityList.get(0).getTipoTransacao());
        assertEquals(3, findedPagamentoEntityList.get(0).getParcelas());
        assertEquals("1234********5678", findedPagamentoEntityList.get(0).getCartao());
        assertEquals("AUTH123", findedPagamentoEntityList.get(0).getCodigoAutorizacao());
        assertEquals(987654, findedPagamentoEntityList.get(0).getNsu());
        assertEquals("VISA", findedPagamentoEntityList.get(0).getBandeira());
        assertEquals(150.00, findedPagamentoEntityList.get(0).getValor());
    }

    @Test
    @DisplayName("Test given PagamentoObject when findByCartaoAndBandeiraAndNsuAndCodigoAutorizacaoAndParcelas then return PagamentoObject")
    void testGivenPagamentoObject_whenFindByCartaoAndBandeiraAndNsuAndCodigoAutorizacaoAndParcelas_thenReturnPagamento() {
        //Given
        given(pagamentoRepository.findByCartaoAndBandeiraAndNsuAndCodigoAutorizacaoAndParcelas(
                anyString(),
                anyString(),
                anyInt(),
                anyString(),
                anyInt()
        )).willReturn(Optional.of(pagamento));

        //When
        Optional<Pagamento> findedPagamentoEntity = pagamentoGateway.findByCartaoAndBandeiraAndNsuAndCodigoAutorizacaoAndParcelas(
                anyString(),
                anyString(),
                anyInt(),
                anyString(),
                anyInt()
        );

        //Then
        assertTrue(findedPagamentoEntity.isPresent());
        assertEquals("CARTAO", findedPagamentoEntity.get().getTipo());
        assertEquals("CREDITO", findedPagamentoEntity.get().getTipoTransacao());
        assertEquals(3, findedPagamentoEntity.get().getParcelas());
        assertEquals("1234********5678", findedPagamentoEntity.get().getCartao());
        assertEquals("AUTH123", findedPagamentoEntity.get().getCodigoAutorizacao());
        assertEquals(987654, findedPagamentoEntity.get().getNsu());
        assertEquals("VISA", findedPagamentoEntity.get().getBandeira());
        assertEquals(150.00, findedPagamentoEntity.get().getValor());
    }

}