package br.com.transacao.infrastructure.database;

import br.com.transacao.domain.entities.StatusTransacao;
import br.com.transacao.domain.entities.Transacao;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

public interface TransacaoRepository extends Repository<Transacao, Long> {

    Transacao save(Transacao transacao);
    @Query("SELECT t FROM Transacao t WHERE t.data = :data AND t.quantidadeParcelas = :quantidadeParcelas AND t.valor = :valor AND t.tipoTransacao = :tipoTransacao AND t.cartao = :cartao AND t.codigoAutorizacao = :codigoAutorizacao AND t.nsu = :nsu AND t.bandeira = :bandeira AND t.status = :status AND t.parcelas IS NOT EMPTY")
    Optional<Transacao> findByAtributos(
            @Param("data") Date data,
            @Param("quantidadeParcelas") Integer quantidadeParcelas,
            @Param("valor") BigDecimal valor,
            @Param("tipoTransacao") String tipoTransacao,
            @Param("cartao") String cartao,
            @Param("codigoAutorizacao") String codigoAutorizacao,
            @Param("nsu") Integer nsu,
            @Param("bandeira") String bandeira,
            @Param("status") StatusTransacao status
    );

}
