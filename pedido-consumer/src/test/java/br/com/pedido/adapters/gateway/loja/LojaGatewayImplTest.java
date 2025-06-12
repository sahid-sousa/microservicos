package br.com.pedido.adapters.gateway.loja;

import br.com.pedido.domain.entities.Loja;
import br.com.pedido.domain.entities.Pedido;
import br.com.pedido.infrastructure.database.LojaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LojaGatewayImplTest {

    @InjectMocks
    LojaGatewayImpl lojaGateway;

    @Mock
    private LojaRepository lojaRepository;

    private Loja loja;

    @BeforeEach
    public void setup() {
        //Given
        loja = new Loja();
        loja.setCodigo("LOJA-001");
        loja.setCnpj("12345678000199");

        Pedido pedido = new Pedido();
        pedido.setUuid(UUID.randomUUID().toString());
        pedido.setCodigo("PED123456");
        pedido.setValor(150.00);
        pedido.setData(new Date());
        pedido.setFaturado(true);
        pedido.setConciliado(false);

        loja.adicionarPedido(pedido);
    }

    @Test
    @DisplayName("Test given Loja Object  when save Loja then return saved Loja")
    void testGivenLojaObject_whenSaveLoja_thenReturnSavedLoja() {
        //Given
        given(lojaRepository.save(loja)).willReturn(loja);

        //When
        Loja entity = lojaGateway.save(loja);

        //Then
        assertNotNull(entity);
        assertEquals("LOJA-001", entity.getCodigo());
        assertEquals("12345678000199", entity.getCnpj());
    }

    @Test
    @DisplayName("Test given LojaObject when findById then return LojaObject")
    void testGivenLojaObject_whenFindById_thenReturnLojaObject() {
        //Given
        given(lojaRepository.findById(anyLong())).willReturn(Optional.of(loja));

        //When
        Optional<Loja> findedLojaEntity = lojaGateway.findById(anyLong());

        //Then
        assertTrue(findedLojaEntity.isPresent());
        assertEquals("LOJA-001", findedLojaEntity.get().getCodigo());
        assertEquals("12345678000199", findedLojaEntity.get().getCnpj());
    }

    @Test
    @DisplayName("Test given LojaObject when findByCnpj then return LojaObject")
    void testGivenLojaObject_whenFindByCnpj_thenReturnLojaObject() {
        //Given
        given(lojaRepository.findByCnpj(anyString())).willReturn(Optional.of(loja));

        //When
        Optional<Loja> findedLojaEntity = lojaGateway.findByCnpj(anyString());

        //Then
        assertTrue(findedLojaEntity.isPresent());
        assertEquals("LOJA-001", findedLojaEntity.get().getCodigo());
        assertEquals("12345678000199", findedLojaEntity.get().getCnpj());
    }

    @Test
    @DisplayName("Test given LojaObject when findByCodigo then return LojaObject")
    void testGivenLojaObject_whenFindByCodigo_thenReturnLojaObject() {
        //Given
        given(lojaRepository.findByCodigo(anyString())).willReturn(loja);

        //WHen
        Loja findedLojaEntity = lojaGateway.findByCodigo(anyString());

        //Then
        assertNotNull(findedLojaEntity);
        assertEquals("LOJA-001", findedLojaEntity.getCodigo());
        assertEquals("12345678000199", findedLojaEntity.getCnpj());
    }

}