package br.com.pedido.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pagamento_generator")
    @SequenceGenerator(name = "pagamento_generator", sequenceName = "pagamento_seq", allocationSize = 1)
    private Long id;
    private String tipo;
    private String tipoTransacao;
    private Integer parcelas;
    private String cartao;
    private String codigoAutorizacao;
    private Integer nsu;
    private String bandeira;
    private Double valor;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Pedido pedido;

}
