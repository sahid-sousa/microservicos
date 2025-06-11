package br.com.transacao.infrastructure.broker;

import br.com.commons.dto.transacao.TransacaoDto;
import br.com.transacao.application.usecases.transacao.CriarTransacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@ExtendWith(MockitoExtension.class)
public class TransacaoConsumerTest {

    @Spy
    @InjectMocks
    TransacaoConsumer transacaoConsumer;

    @Mock
    CriarTransacao criarTransacao;

    private TransacaoDto transacaoDto;

    @BeforeEach
    public void setup() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        transacaoDto = new TransacaoDto(
                "LOJA123",
                "PEDIDO456",
                "uuid-abc-123",
                formatter.parse("07/05/2025"),
                1,
                new BigDecimal("100.00"),
                new BigDecimal("2.50"),
                "DEBITO",
                "123456******7890",
                "AUTH123456",
                987654,
                "VISA"
        );
    }


    @Test
    @DisplayName("Test consumer TransacaoDto")
    public void testConsumerTransacaoDto() {
        //When
        transacaoConsumer.consumer(transacaoDto);

        //Then
        verify(criarTransacao, times(1)).executar(any(TransacaoDto.class));
    }
}
