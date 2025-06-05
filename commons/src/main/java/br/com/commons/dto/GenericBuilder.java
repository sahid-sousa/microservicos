package br.com.commons.dto;

import java.util.function.Consumer;

public class GenericBuilder<T> {

    private final T entity;

    public GenericBuilder(T entity) {
        this.entity = entity;
    }

    public static<T> GenericBuilder<T> of(Class<T> instance) {
        try {
            return new GenericBuilder<>(instance.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public GenericBuilder<T> with(Consumer<T> setter) {
        setter.accept(entity);
        return this;
    }

    public T build() {
        return entity;
    }

}
