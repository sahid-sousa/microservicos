package br.com.transacao.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transacao_generator")
    @SequenceGenerator(name= "transacao_generator", sequenceName = "transacao_seq", allocationSize = 1)
    private Long id;

    @UuidGenerator
    @Column(unique = true, updatable = false, columnDefinition = "VARCHAR(36)")
    private String uuid;

    private String codigoLoja;
    private String codigoPedido;
    private String uuidVenda;
    private Date data;
    private Integer quantidadeParcelas;
    private BigDecimal valor;
    private BigDecimal taxa;
    private String tipoTransacao;
    private StatusTransacao status;
    private String cartao;
    private String codigoAutorizacao;
    private Integer nsu;
    private String bandeira;


    @OneToMany(mappedBy = "transacao", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Parcela> parcelas = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = StatusTransacao.PENDENTE;
        }
    }

    public void adicionarParcela(Parcela parcela) {
        parcela.setTransacao(this);
        this.parcelas.add(parcela);
    }

}
