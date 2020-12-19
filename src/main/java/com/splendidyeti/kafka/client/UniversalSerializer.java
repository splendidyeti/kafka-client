package com.splendidyeti.kafka.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import static org.apache.commons.lang3.ArrayUtils.EMPTY_BYTE_ARRAY;

public class UniversalSerializer<T> implements Serializer<T> {

    private final FastJackson fastJackson;

    public UniversalSerializer() {
        this.fastJackson = new FastJackson();
    }
    public UniversalSerializer(ObjectMapper objectMapper) {
        this.fastJackson = new FastJackson(objectMapper);
    }

    @Override public void configure(Map<String, ?> configs, boolean isKey) {}

    @Override public void close() {}

    @Override
    public byte[] serialize(String topic, T data) {
        if (data == null) return EMPTY_BYTE_ARRAY;
        return fastJackson.asString(new UniversalWrapper(data)).getBytes(StandardCharsets.UTF_8);
    }
}
