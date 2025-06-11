package br.com.transacao.infrastructure.database;

import br.com.transacao.domain.entities.StatusTransacao;
import br.com.transacao.domain.entities.Transacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TransacaoRepositoryTest {

    @Autowired
    TransacaoRepository transacaoRepository;

    private Transacao transacao;

    @BeforeEach
    public void setup() {
        //Given
        transacao = new Transacao();
        transacao.setCodigoLoja("L00001");
        transacao.setCodigoPedido("P00001");
        transacao.setData(new Date());
        transacao.setQuantidadeParcelas(1);
        transacao.setValor(new BigDecimal(150));
        transacao.setTaxa(new BigDecimal(2));
        transacao. setTipoTransacao("DEBITO");
        transacao. setStatus(StatusTransacao.PENDENTE);
        transacao.setCartao("123456*******789");
        transacao.setCodigoAutorizacao("156352");
        transacao.setNsu(1258);
        transacao.setBandeira("VISA");
    }


    @Test
    @DisplayName("Test given Loja Object  when save Loja then return saved Loja")
    void testGivenTransacaoObject_whenFindByAtributos_thenReturnOptionalTransacao() {
        //When
        Transacao entity = transacaoRepository.save(transacao);

        //Then
        assertNotNull(entity);
        assertEquals("L00001", entity.getCodigoLoja());
        assertEquals("P00001", entity.getCodigoPedido());
    }

}