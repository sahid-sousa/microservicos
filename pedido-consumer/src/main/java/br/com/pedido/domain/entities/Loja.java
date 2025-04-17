package br.com.pedido.domain.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Loja {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "loja_generator")
    @SequenceGenerator(name = "loja_generator", sequenceName = "loja_seq", allocationSize = 1)
    private Long id;

    @UuidGenerator
    @Column(unique = true, updatable = false, columnDefinition = "VARCHAR(36)")
    private String uuid;

    private String codigo;
    private String cnpj;
    @OneToMany(mappedBy = "loja", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();

    public void adicionarPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setLoja(this);
    }

}
