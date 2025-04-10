package br.com.pedido.domain.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String codigo;
    private String cnpj;
    @OneToMany(mappedBy = "loja", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos = new ArrayList<>();

    public void adicionarPedido(Pedido pedido) {
        this.pedidos.add(pedido);
        pedido.setLoja(this);
    }

}
