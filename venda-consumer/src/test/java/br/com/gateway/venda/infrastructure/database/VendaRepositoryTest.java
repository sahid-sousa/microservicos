package br.com.gateway.venda.infrastructure.database;

import br.com.gateway.venda.domain.entities.Venda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class VendaRepositoryTest {

    @Autowired
    private VendaRepository vendaRepository;

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
        //When
        Venda entity = vendaRepository.save(venda);

        //Then
        assertNotNull(entity);
        assertEquals("123456*******789", entity.getCartao());
        assertEquals("156352", entity.getCodigoAutorizacao());
    }

    @Test
    @DisplayName("Test given Venda Object  when save Venda then return saved Venda")
    void testGivenVendaObject_whenFindByAtributos_thenReturnOptionalVenda() {
        //When

        vendaRepository.save(venda);
        Optional<Venda> findedVendaEntity = vendaRepository.findByAtributos(
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
        assertTrue(findedVendaEntity.get().getId() > 0);
        assertEquals("123456*******789", findedVendaEntity.get().getCartao());
        assertEquals("156352", findedVendaEntity.get().getCodigoAutorizacao());
    }

}