package br.com.gateway.venda.application.usecases;

import br.com.commons.dto.venda.VendaDetailDto;
import br.com.gateway.venda.application.usecase.CriarVenda;
import br.com.gateway.venda.domain.entities.Venda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CriarVendaTest {

    @Mock
    CriarVenda criarVenda;

    private VendaDetailDto vendaDetailDto;
    private Venda venda;

    @BeforeEach
    public void setup() throws ParseException {

        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        vendaDetailDto = new VendaDetailDto(
                "uuid-venda-001",
                new Date(),
                "123456******7890",
                "AUTZ987654",
                123456,
                "MASTERCARD",
                5,
                "DEBITO",
                new BigDecimal("350.75"),
                new BigDecimal("7.25")
        );

        venda = new Venda();
        venda.setDataVenda(formatter.parse("07/05/2025"));
        venda.setCartao("123456*******7890");
        venda.setCodigoAutorizacao("156352");
        venda.setNsu(1258);
        venda.setBandeira("VISA");
        venda.setParcelas(1);
        venda.setTipoTransacao("DEBITO");
        venda.setValorTransacao(new BigDecimal(100));
        venda.setTaxaTransacao(new BigDecimal(1));
    }


    @Test
    @DisplayName("Test Executar Venda")
    public void testExecutarVenda() {
        //Given
        given(criarVenda.executar(any())).willReturn(venda);

        //When
        Venda entity = criarVenda.executar(vendaDetailDto);

        //Then
        assertNotNull(entity);
        assertEquals("156352", entity.getCodigoAutorizacao());
        assertEquals("123456*******7890", entity.getCartao());
        assertEquals(1258, entity.getNsu());
        assertEquals("VISA", entity.getBandeira());
        assertEquals(1, entity.getParcelas());
        assertEquals("DEBITO", entity.getTipoTransacao());
    }

    @Test
    @DisplayName("Test Criar Venda")
    public void testCriarVenda() {
        //Given
        given(criarVenda.criarVenda(any())).willReturn(venda);

        //When
        Venda entity = criarVenda.criarVenda(vendaDetailDto);

        //Then
        assertNotNull(entity);
        assertEquals("156352", entity.getCodigoAutorizacao());
        assertEquals("123456*******7890", entity.getCartao());
        assertEquals(1258, entity.getNsu());
        assertEquals("VISA", entity.getBandeira());
        assertEquals(1, entity.getParcelas());
        assertEquals("DEBITO", entity.getTipoTransacao());
    }

}
