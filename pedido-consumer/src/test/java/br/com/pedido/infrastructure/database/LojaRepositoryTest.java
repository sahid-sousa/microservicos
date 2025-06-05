package br.com.pedido.infrastructure.database;

import br.com.pedido.domain.entities.Loja;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class LojaRepositoryTest {

    @Autowired
    LojaRepository repository;

    private Loja loja;

    @BeforeEach
    public void setup() {
        //Given
        loja = new Loja();
        loja.setCodigo("L0001");
        //loja.setUuid("07a2b67f-7d0b-400d-b6ee-b30688080f88");
        loja.setCnpj("11137341000120");
    }

    @Test
    @DisplayName("Test given Loja Object  when save Loja then return saved Loja")
    void testGivenLojaObject_whenSaveLoja_thenReturnSavedLoja() {
        //When
        Loja entity = repository.save(loja);
        //Then
        assertNotNull(entity);
        assertEquals("L0001", entity.getCodigo());
        assertEquals("11137341000120", entity.getCnpj());
        assertTrue(entity.getId() > 0);
    }

    @Test
    @DisplayName("Test given LojaObject when findById then return LojaObject")
    void testGivenLojaObject_whenFindById_thenReturnLojaObject() {
        //When
        Loja entity = repository.save(loja);
        Optional<Loja> findedLojaEntity = repository.findById(entity.getId());
        //Then
        assertTrue(findedLojaEntity.isPresent());
        assertEquals(entity.getCodigo(), findedLojaEntity.get().getCodigo());
        assertEquals(entity.getUuid(), findedLojaEntity.get().getUuid());
        assertEquals(entity.getCnpj(), findedLojaEntity.get().getCnpj());
        assertTrue(findedLojaEntity.get().getId() > 0);
    }

    @Test
    @DisplayName("Test given LojaObject when findByCnpj then return LojaObject")
    void testGivenLojaObject_whenFindByCnpj_thenReturnLojaObject() {
        //When
        Loja entity = repository.save(loja);
        Optional<Loja> findedLojaEntity = repository.findByCnpj(entity.getCnpj());
        //Then
        assertTrue(findedLojaEntity.isPresent());
        assertEquals(entity.getCodigo(), findedLojaEntity.get().getCodigo());
        assertEquals(entity.getUuid(), findedLojaEntity.get().getUuid());
        assertEquals(entity.getCnpj(), findedLojaEntity.get().getCnpj());
        assertTrue(findedLojaEntity.get().getId() > 0);
    }

    @Test
    @DisplayName("Test given LojaObject when findByCodigo then return LojaObject")
    void testGivenLojaObject_whenFindByCodigo_thenReturnLojaObject() {
        //When
        Loja entity = repository.save(loja);
        Loja findedLojaEntity = repository.findByCodigo(entity.getCodigo());
        //Then
        assertNotNull(findedLojaEntity);
        assertEquals(entity.getCodigo(), findedLojaEntity.getCodigo());
        assertEquals(entity.getUuid(), findedLojaEntity.getUuid());
        assertEquals(entity.getCnpj(), findedLojaEntity.getCnpj());
        assertTrue(findedLojaEntity.getId() > 0);
    }

}