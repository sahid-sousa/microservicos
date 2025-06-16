package br.com.gateway.venda.adapters.gateway;

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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VendaGatewayTest {

    @Mock
    VendaGateway vendaGateway;

    private Venda venda;

    @BeforeEach
    public void setup() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        venda = new Venda();
        venda.setDataVenda(formatter.parse("07/05/2025"));
        venda.setCartao("123456*******789");
        venda.setCodigoAutorizacao("156352");
        venda.setNsu(1258);
        venda.setBandeira("VISA");
        venda.setParcelas(1);
        venda.setTipoTransacao("DEBITO");
        venda.setValorTransacao(new BigDecimal(100));
        venda.setTaxaTransacao(new BigDecimal(1));
    }

    @Test
    @DisplayName("Test given Venda Object  when save Venda then return saved Venda")
    void testGivenVendaObject_whenSave_thenReturnSavedVenda() {
        //Given
        given(vendaGateway.save(any())).willReturn(venda);

        //When
        Venda entity = vendaGateway.save(venda);

        //Then
        assertNotNull(entity);
        assertEquals("123456*******789", entity.getCartao());
        assertEquals("156352", entity.getCodigoAutorizacao());
    }

    @Test
    @DisplayName("Test given Venda Object  when save Venda then return saved Venda")
    void testGivenVendaObject_whenFindByAtributos_thenReturnOptionalVenda() {
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
        Optional<Venda> findedVendaEntity = vendaGateway.findByAtributos(
                venda.getDataVenda(),
                venda.getCartao(),
                venda.getCodigoAutorizacao(),
                venda.getNsu(),
                venda.getBandeira(),
                venda.getParcelas(),
                venda.getTipoTransacao()
        );

        //Then
        assertTrue(findedVendaEntity.isPresent());
        assertEquals("123456*******789", findedVendaEntity.get().getCartao());
        assertEquals("156352", findedVendaEntity.get().getCodigoAutorizacao());
    }

}