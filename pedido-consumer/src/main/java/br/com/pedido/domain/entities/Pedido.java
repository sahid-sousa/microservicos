package br.com.pedido.domain.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String codigo;
    private Double valor;
    private Date data;
    private Boolean faturado;
    @ManyToOne(cascade = CascadeType.ALL)
    private Loja loja;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pagamento> pagamentos = new ArrayList<>();

    public void adicionarPagamento(Pagamento pagamento) {
        this.pagamentos.add(pagamento);
        pagamento.setPedido(this);
    }

}
