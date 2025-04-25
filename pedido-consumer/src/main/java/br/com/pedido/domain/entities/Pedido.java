package br.com.pedido.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pagamento_generator")
    @SequenceGenerator(name = "pagamento_generator", sequenceName = "pagamento_seq", allocationSize = 1)
    private Long id;

    @UuidGenerator
    @Column(unique = true, updatable = false, columnDefinition = "VARCHAR(36)")
    private String uuid;

    private String codigo;
    private Double valor;
    private Date data;
    private Boolean faturado;
    private Boolean conciliado;
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Loja loja;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagamento> pagamentos = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (conciliado == null) {
            conciliado = false;
        }
    }

    public void adicionarPagamento(Pagamento pagamento) {
        this.pagamentos.add(pagamento);
        pagamento.setPedido(this);
    }

}
