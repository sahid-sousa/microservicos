package br.com.gateway.venda.application.usecases;

import br.com.commons.dto.pedido.LojaDto;
import br.com.commons.dto.pedido.PagamentoDto;
import br.com.commons.dto.pedido.PedidoDto;
import br.com.commons.dto.venda.VendaDetailDto;
import br.com.gateway.venda.application.usecase.BuscarVenda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BuscarVendaTest {

    @Mock
    BuscarVenda buscarVenda;

    private PedidoDto pedidoDto;
    private VendaDetailDto novaVendaDetailDto;

    @BeforeEach
    public void setup() {

        pedidoDto = new PedidoDto(
                UUID.randomUUID().toString(), // uuid do pedido
                "P0013570",                   // codigo
                100.0,                        // valor
                new Date(1688180400000L),     // data (timestamp em milissegundos)
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

        PagamentoDto pagamento = pedidoDto.pagamentos().get(0);

        novaVendaDetailDto = new VendaDetailDto(
                UUID.randomUUID().toString(),
                pedidoDto.data(),
                pagamento.cartao(),
                pagamento.codigoAutorizacao(),
                pagamento.nsu(),
                pagamento.bandeira(),
                pagamento.parcelas(),
                pagamento.tipoTransacao(),
                new BigDecimal("100.0"),
                new BigDecimal("7.25")
        );

    }

    @Test
    @DisplayName("Test buscar venda")
    public void testBuscarVenda() {
        //Given
        given(buscarVenda.executar(any())).willReturn(List.of(novaVendaDetailDto));

        //When
        List<VendaDetailDto> vendas = buscarVenda.executar(pedidoDto);

        //Then
        assertFalse(vendas.isEmpty());
        assertEquals(1, vendas.size());
    }


}
