package br.com.transacao.infrastructure.database;

import br.com.transacao.domain.entities.Parcela;
import br.com.transacao.domain.entities.StatusTransacao;
import br.com.transacao.domain.entities.Transacao;
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
class TransacaoRepositoryTest {

    @Autowired
    TransacaoRepository transacaoRepository;

    private Transacao transacao;

    @BeforeEach
    public void setup() throws ParseException {
        //Given
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        transacao = new Transacao();
        transacao.setCodigoLoja("L00001");
        transacao.setCodigoPedido("P00001");
        transacao.setData(formatter.parse("07/05/2025"));
        transacao.setQuantidadeParcelas(1);
        transacao.setValor(new BigDecimal(150));
        transacao.setTaxa(new BigDecimal(2));
        transacao.setTipoTransacao("DEBITO");
        transacao.setStatus(StatusTransacao.PENDENTE);
        transacao.setCartao("123456*******789");
        transacao.setCodigoAutorizacao("156352");
        transacao.setNsu(1258);
        transacao.setBandeira("VISA");

        Parcela parcela = new Parcela();
        parcela.setData(formatter.parse("18/06/2025"));
        parcela.setNumero(1);
        parcela.setValorBruto(new BigDecimal("100.00"));
        parcela.setValorLiquido(new BigDecimal("99.00"));
        parcela.setValorDesconto(new BigDecimal("1"));

        transacao.adicionarParcela(parcela);
    }


    @Test
    @DisplayName("Test given Transacao Object  when save Transacao then return saved Transacao")
    void testGivenTransacaoObject_whenSave_thenReturnSavedTransacao() {
        //When
        Transacao entity = transacaoRepository.save(transacao);

        //Then
        assertNotNull(entity);
        assertEquals("L00001", entity.getCodigoLoja());
        assertEquals("P00001", entity.getCodigoPedido());
    }



    @Test
    @DisplayName("Test given Optional Transacao Object  when save Transacao then return saved Optional Transacao")
    void testGivenTransacaoObject_whenFindByAtributos_thenReturnOptionalTransacao() {
        //When
        Transacao entity = transacaoRepository.save(transacao);

        Optional<Transacao> findedTransacaoOptionalEntity = transacaoRepository.findByAtributos(
                entity.getData(),
                entity.getQuantidadeParcelas(),
                entity.getValor(),
                entity.getTipoTransacao(),
                entity.getCartao(),
                entity.getCodigoAutorizacao(),
                entity.getNsu(),
                entity.getBandeira(),
                entity.getStatus()
        );

        //Then
        assertTrue(findedTransacaoOptionalEntity.isPresent());
        assertEquals("L00001", findedTransacaoOptionalEntity.get().getCodigoLoja());
        assertEquals("P00001", findedTransacaoOptionalEntity.get().getCodigoPedido());
    }


}