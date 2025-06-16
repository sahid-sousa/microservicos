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
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CriarVendaImplTest {

    @Spy
    @InjectMocks
    CriarVendaImpl criarVenda;

    @Mock
    VendaGateway vendaGateway;

    private VendaDetailDto novaVendaDetailDto;
    private Venda venda;

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
    @DisplayName("Test Executar Venda")
    public void testExecutarVenda() {
        //When
        criarVenda.executar(novaVendaDetailDto);

        //Then
        verify(vendaGateway, times(1)).save(any(Venda.class));
    }

    @Test
    @DisplayName("Test Criar Venda")
    public void testCriarVenda() {
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
        Venda entity = criarVenda.criarVenda(novaVendaDetailDto);

        //Then
        assertNotNull(entity);
    }

    @Test
    @DisplayName("Test Criar Nova Venda")
    public void testCriarNoveVenda() {
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
        Venda entity = criarVenda.criarVenda(novaVendaDetailDto);

        //Then
        assertNotNull(entity);
    }


}