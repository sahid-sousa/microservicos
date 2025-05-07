package br.com.transacao.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Parcela {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "parcela_generator")
    @SequenceGenerator(name = "parcela_generator", sequenceName = "parcela_seq", allocationSize = 1)
    private Long id;

    @UuidGenerator
    @Column(unique = true, updatable = false, columnDefinition = "VARCHAR(36)")
    private String uuid;

    private Date data;
    private Integer numero;
    private BigDecimal valorBruto;
    private BigDecimal valorLiquido;
    private BigDecimal valorDesconto;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Transacao transacao;
}
