package br.com.gateway.venda.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "venda_generator")
    @SequenceGenerator(name = "venda_generator", sequenceName = "venda_seq", allocationSize = 1)
    private Long id;

    @UuidGenerator
    @Column(unique = true, updatable = false, columnDefinition = "VARCHAR(36)")
    private String uuid;

    private Date dataVenda;
    private String cartao;
    private String codigoAutorizacao;
    private Integer nsu;
    private String bandeira;
    private Integer parcelas;
    private String tipoTransacao;
    private BigDecimal valorTransacao;
    private BigDecimal taxaTransacao;

}
