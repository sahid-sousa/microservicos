package br.com.pedido.infrastructure.database;

import br.com.pedido.domain.entities.Loja;
import br.com.pedido.domain.entities.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PedidoRepositoryTest {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    LojaRepository lojaRepository;

    private Loja loja;
    private Pedido pedido;

    @BeforeEach
    public void setup() {
        //Give
        loja = new Loja();
        loja.setCodigo("LOJA-001");
        loja.setCnpj("12345678000199");

        pedido = new Pedido();
        pedido.setUuid(UUID.randomUUID().toString());
        pedido.setCodigo("PED123456");
        pedido.setValor(150.00);
        pedido.setData(new Date());
        pedido.setFaturado(true);
        pedido.setConciliado(false);

        loja.adicionarPedido(pedido);
    }

    @Test
    @DisplayName("Test given PedidoObject when save Pedido then return saved Pedido")
    void testGivenPedidoObject_whenSavePedido_thenReturnSavedPedido() {
        //When
        lojaRepository.save(loja);
        Pedido pedidoEntity = pedidoRepository.save(pedido);

        //Then
        assertNotNull(pedidoEntity);
        assertEquals("PED123456", pedidoEntity.getCodigo());
        assertEquals(150.00, pedidoEntity.getValor());
        assertTrue(pedidoEntity.getFaturado());
        assertFalse(pedidoEntity.getConciliado());
    }

    @Test
    @DisplayName("Test given PedidoObject when findById then return Pedido Object")
    void testGivenPedidoObject_whenFindById_thenReturnPagamentoObject() {
        //When
        lojaRepository.save(loja);
        Pedido pedidoEntity = pedidoRepository.save(pedido);
        Pedido findedPedidoEntity = pedidoRepository.findById(pedidoEntity.getId());

        //Then
        assertNotNull(findedPedidoEntity);
        assertEquals("PED123456", findedPedidoEntity.getCodigo());
        assertEquals(150.00, findedPedidoEntity.getValor());
        assertTrue(findedPedidoEntity.getFaturado());
        assertFalse(findedPedidoEntity.getConciliado());
    }

    @Test
    @DisplayName("Test given PedidoObject when findAllByLoja then return PedidoList")
    void testGivenPedidoObject_whenFindAllByLoja_thenReturnPedidoList() {
        //When
        Loja lojaEntity = lojaRepository.save(loja);
        pedidoRepository.save(pedido);

        List<Pedido> findedPedidoEntityList = pedidoRepository.findAllByLoja(lojaEntity);

        //Then
        assertFalse(findedPedidoEntityList.isEmpty());
        assertEquals("PED123456", findedPedidoEntityList.get(0).getCodigo());
        assertEquals(150.00, findedPedidoEntityList.get(0).getValor());
        assertTrue(findedPedidoEntityList.get(0).getFaturado());
        assertFalse(findedPedidoEntityList.get(0).getConciliado());
    }

    @Test
    @DisplayName("Test given Pedido object when findByCodigo' then return Pedido object")
    void testGivenPedidoObject_whenFindByCodigo_thenReturnPedidoObject() {
        // When
        lojaRepository.save(loja);
        Pedido pedidoEntity = pedidoRepository.save(pedido);
        Optional<Pedido> findedPedidoEntity = pedidoRepository.findByCodigo(pedidoEntity.getCodigo());

        // Then
        assertTrue(findedPedidoEntity.isPresent());
        assertEquals("PED123456", findedPedidoEntity.get().getCodigo());
        assertEquals(150.00, findedPedidoEntity.get().getValor());
        assertTrue(findedPedidoEntity.get().getFaturado());
        assertFalse(findedPedidoEntity.get().getConciliado());
    }

    @Test
    @DisplayName("Test given Pedido object when findByUuid then return Pedido object")
    void testGivenPedidoObject_whenFindByUuid_thenReturnPedidoObject() {
        // When
        lojaRepository.save(loja);
        Pedido pedidoEntity = pedidoRepository.save(pedido);
        Optional<Pedido> findedPedidoEntity = pedidoRepository.findByCodigo(pedidoEntity.getCodigo());
        assertTrue(findedPedidoEntity.isPresent());
        Optional<Pedido> findedPedidoOptionalEntity = pedidoRepository.findByUuid(findedPedidoEntity.get().getUuid());

        // Then
        assertTrue(findedPedidoOptionalEntity.isPresent());
        assertEquals("PED123456", findedPedidoOptionalEntity.get().getCodigo());
        assertEquals(150.00, findedPedidoOptionalEntity.get().getValor());
        assertTrue(findedPedidoOptionalEntity.get().getFaturado());
        assertFalse(findedPedidoOptionalEntity.get().getConciliado());
    }


    @Test
    @DisplayName("Test given PedidoObject when findAllByLoja then return PedidoList")
    void testGivenPedidoIdListObject_whenFindAllStatus_thenReturnPedidoList() {
        //When
        lojaRepository.save(loja);
        pedidoRepository.save(pedido);
        Pageable pageable = PageRequest.of(0, 10000);
        List<Long> findedLongIdList = pedidoRepository.findAllStatus(true, false, pageable);

        //Then
        assertFalse(findedLongIdList.isEmpty());
    }

}