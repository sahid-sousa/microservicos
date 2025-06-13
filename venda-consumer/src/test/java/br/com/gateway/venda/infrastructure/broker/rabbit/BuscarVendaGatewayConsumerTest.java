package br.com.gateway.venda.infrastructure.broker.rabbit;

import br.com.commons.dto.pedido.LojaDto;
import br.com.commons.dto.pedido.PagamentoDto;
import br.com.commons.dto.pedido.PedidoDto;
import br.com.gateway.venda.application.usecase.BuscarVenda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarVendaGatewayConsumerTest {

    @Spy
    @InjectMocks
    BuscarVendaGatewayConsumer buscarVendaGatewayConsumer;

    @Mock
    BuscarVenda buscarVenda;

    private PedidoDto pedidoDto;

    @BeforeEach
    public void setup() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        pedidoDto = new PedidoDto(
                UUID.randomUUID().toString(), // uuid do pedido
                "P0013570",                   // codigo
                100.0,                        // valor
                formatter.parse("08/05/2025"),     // data (timestamp em milissegundos)
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
    }

    @Test
    @DisplayName("Test Buscar Venda Gateway Consumer")
    public void testBuscarVendaGatewayConsumer() {
        //When
        buscarVendaGatewayConsumer.consumer(pedidoDto);

        //Then
        verify(buscarVenda, times(1)).executar(any(PedidoDto.class));
    }


}