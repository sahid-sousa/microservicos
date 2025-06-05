package br.com.gateway.venda.application.usecases;

import br.com.commons.dto.pedido.LojaDto;
import br.com.commons.dto.pedido.PagamentoDto;
import br.com.commons.dto.pedido.PedidoDto;
import br.com.commons.dto.venda.VendaDetailDto;
import br.com.gateway.venda.application.usecase.BuscarVenda;
import br.com.gateway.venda.application.usecase.CriarVenda;
import br.com.gateway.venda.domain.entities.Venda;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class BuscarVendaTest {


    @Autowired
    CriarVenda criarVenda;

    @Autowired
    BuscarVenda buscarVenda;

    @Test
    public void deveBuscarVenda() {
        PedidoDto pedido = new PedidoDto(
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

        PagamentoDto pagamento = pedido.pagamentos().get(0);

        VendaDetailDto novaVendaDetail = new VendaDetailDto(
                UUID.randomUUID().toString(),
                pedido.data(),
                pagamento.cartao(),
                pagamento.codigoAutorizacao(),
                pagamento.nsu(),
                pagamento.bandeira(),
                pagamento.parcelas(),
                pagamento.tipoTransacao(),
                new BigDecimal("100.0"),
                new BigDecimal("7.25")
        );

        Venda venda = criarVenda.executar(novaVendaDetail);

        List<VendaDetailDto> vendas = buscarVenda.executar(pedido);

        Assertions.assertNotNull(venda);
        Assertions.assertEquals(1, vendas.size());

    }


}
