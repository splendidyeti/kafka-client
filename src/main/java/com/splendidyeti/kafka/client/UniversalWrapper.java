package com.splendidyeti.kafka.client;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UniversalWrapper<T> {
    String type;
    T data;

    public UniversalWrapper(T data) {
        this.type = data == null ? "java.lang.Object" : data.getClass().getName();
        this.data = data;
    }
}
