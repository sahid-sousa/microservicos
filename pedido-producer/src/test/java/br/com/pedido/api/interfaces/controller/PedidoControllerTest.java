package br.com.pedido.api.interfaces.controller;

import br.com.commons.dto.pedido.LojaDto;
import br.com.commons.dto.pedido.PagamentoDto;
import br.com.commons.dto.pedido.PedidoDto;
import br.com.pedido.api.infrastructure.broker.Producer;
import br.com.pedido.api.interfaces.controllers.PedidoController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PedidoControllerTest {


    @InjectMocks
    private PedidoController pedidoController;

    @Mock
    Producer<PedidoDto> producer;

    @Test
    public void deveRetornarSucessoAoReceberPedido() throws Exception {

        LojaDto loja = new LojaDto("loja-uuid-001","LOJA001", "12.345.678/0001-99");
        PagamentoDto pagamento = new PagamentoDto(
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
        PedidoDto pedido = new PedidoDto(
                "pedido-uuid-001",
                "PED123456",
                200.00,
                new Date(),
                true,
                false,
                loja,
                List.of(pagamento)
        );

        ResponseEntity<?> response = pedidoController.pedido(pedido);
        verify(producer, times(1)).enviar(any(PedidoDto.class));
        Assertions.assertEquals(200, response.getStatusCode().value());

    }
}
