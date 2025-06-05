package br.com.pedido.infrastructure.broker;

public interface RpcProducer<T> {
    void enviar(T t);
}
