package br.com.gateway.venda.infrastructure.broker.rabbit;

import br.com.commons.dto.venda.VendaDetailDto;
import br.com.gateway.venda.application.usecase.CriarVenda;
import org.junit.jupiter.api.BeforeEach;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VendaGatewayConsumerTest {

    @Spy
    @InjectMocks
    VendaGatewayConsumer vendaGatewayConsumer;

    @Mock
    CriarVenda criarVenda;

    private VendaDetailDto vendaDetailDto;

    @BeforeEach
    public void setup() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        vendaDetailDto = new VendaDetailDto(
                "uuid-venda-001",
                formatter.parse("08/05/2025"),
                "123456******7890",
                "AUTZ987654",
                123456,
                "MASTERCARD",
                5,
                "DEBITO",
                new BigDecimal("350.75"),
                new BigDecimal("7.25")
        );

    }

    @Test
    @DisplayName("Test Venda Gateway Consumer")
    public void testVendaGatewayConsumer() {
        //When
        vendaGatewayConsumer.consumer(vendaDetailDto);

        //Then
        verify(criarVenda, times(1)).executar(any(VendaDetailDto.class));
    }


}