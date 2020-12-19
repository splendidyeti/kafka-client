package com.splendidyeti.kafka.client;

import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class UniversalSerde<T> implements Serde<T> {

    private final Serializer<T> serializer = new UniversalSerializer<T>();
    private final Deserializer<T> deserializer = new UniversalDeserializer<T>();

    @Override public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override public void close() {}

    @Override
    public Serializer<T> serializer() {
        return serializer;
    }

    @Override
    public Deserializer<T> deserializer() {
        return deserializer;
    }
}
