package br.com.gateway.venda.application.services;

import br.com.commons.dto.pedido.LojaDto;
import br.com.commons.dto.pedido.PagamentoDto;
import br.com.commons.dto.pedido.PedidoDto;
import br.com.commons.dto.venda.VendaDetailDto;
import br.com.gateway.venda.adapters.gateway.VendaGateway;
import br.com.gateway.venda.domain.entities.Venda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BuscarVendaImplTest {

    @InjectMocks
    BuscarVendaImpl buscarVenda;

    @Mock
    VendaGateway vendaGateway;

    private PedidoDto pedidoDto;
    private Venda venda;

    @BeforeEach
    public void setup() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        pedidoDto = new PedidoDto(
                UUID.randomUUID().toString(), // uuid do pedido
                "P0013570",                   // codigo
                100.0,                        // valor
                formatter.parse("07/05/2025"),     // data
                false,                        // faturado
                false,                        // conciliado (assumido como false por ausência no JSON)
                new LojaDto(
                        UUID.randomUUID().toString(), // uuid da loja
                        "L001",
                        "03235289000116"
                ),
                List.of(
                        new PagamentoDto(
                                UUID.randomUUID().toString(), // uuid do pagamento
                                "CARTAO",
                                "DEBITO",
                                1,
                                "645123*4651",
                                "ACD465",
                                100000,
                                "VISA",
                                100.0
                        )
                )
        );


        venda = new Venda();
        venda.setDataVenda(formatter.parse("07/05/2025"));
        venda.setCartao("645123*4651");
        venda.setCodigoAutorizacao("ACD465");
        venda.setNsu(100000);
        venda.setBandeira("VISA");
        venda.setParcelas(1);
        venda.setTipoTransacao("DEBITO");
        venda.setValorTransacao(new BigDecimal(100));
        venda.setTaxaTransacao(new BigDecimal(1));
    }

    @Test
    @DisplayName("Test Buscar Venda Existente")
    public void testBuscarVendaExistente() {
        //Given
        given(vendaGateway.findByAtributos(
                any(),
                anyString(),
                anyString(),
                anyInt(),
                anyString(),
                anyInt(),
                any()
        )).willReturn(Optional.of(venda));

        //When
        List<VendaDetailDto> vendas = buscarVenda.executar(pedidoDto);

        //Then
        assertFalse(vendas.isEmpty());
        assertEquals("ACD465", vendas.get(0).codigoAutorizacao());
        assertEquals("645123*4651", vendas.get(0).cartao());
        assertEquals(100000, vendas.get(0).nsu());
        assertEquals("VISA", vendas.get(0).bandeira());
        assertEquals(1, vendas.get(0).parcelas());
    }

    @Test
    @DisplayName("Test Buscar Venda Inexistente")
    public void testBuscarVendaInexistente() {
        //Given
        given(vendaGateway.findByAtributos(
                any(),
                anyString(),
                anyString(),
                anyInt(),
                anyString(),
                anyInt(),
                any()
        )).willReturn(Optional.empty());

        //When
        List<VendaDetailDto> vendas = buscarVenda.executar(pedidoDto);

        //Then
        assertTrue(vendas.isEmpty());
    }

}