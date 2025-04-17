package br.com.gateway.venda.infrastructure.database;

import br.com.gateway.venda.domain.entities.Venda;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface VendaRepository extends Repository<Venda, Long> {

    Venda save(Venda venda);
    @Query("""
    SELECT v FROM Venda v
    WHERE v.dataVenda = :dataVenda
      AND v.cartao = :cartao
      AND v.codigoAutorizacao = :codigoAutorizacao
      AND v.nsu = :nsu
      AND v.bandeira = :bandeira
      AND v.parcelas = :parcelas
      AND v.tipoTransacao = :tipoTransacao
    """)
    Optional<Venda> findByAtributos(
            @Param("dataVenda") Date dataVenda,
            @Param("cartao") String cartao,
            @Param("codigoAutorizacao") String codigoAutorizacao,
            @Param("nsu") Integer nsu,
            @Param("bandeira") String bandeira,
            @Param("parcelas") Integer parcelas,
            @Param("tipoTransacao") String tipoTransacao
    );
}
