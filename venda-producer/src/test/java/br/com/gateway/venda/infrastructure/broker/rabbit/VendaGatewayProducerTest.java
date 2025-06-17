package br.com.gateway.venda.infrastructure.broker.rabbit;

import br.com.commons.dto.pedido.LojaDto;
import br.com.commons.dto.pedido.PagamentoDto;
import br.com.commons.dto.pedido.PedidoDto;
import br.com.commons.dto.venda.VendaDetailDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class VendaGatewayProducerTest {

    @Spy
    @InjectMocks
    VendaGatewayProducer vendaGatewayProducer;

    @Mock
    RabbitTemplate rabbitTemplate;

    private VendaDetailDto novaVendaDetailDto;

    @BeforeEach
    public void setup() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        PedidoDto pedidoDto = new PedidoDto(
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

        PagamentoDto pagamento = pedidoDto.pagamentos().get(0);

        novaVendaDetailDto = new VendaDetailDto(
                UUID.randomUUID().toString(),
                formatter.parse("07/05/2025"),
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
    @DisplayName("Test Venda Gateway Producer")
    public void testVendaGatewayProducer() {
        //When
        vendaGatewayProducer.enviar(novaVendaDetailDto);

        //Then
        verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), any(VendaDetailDto.class));
    }


}