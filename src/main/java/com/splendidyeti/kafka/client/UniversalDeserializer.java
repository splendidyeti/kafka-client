package com.splendidyeti.kafka.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.nio.charset.StandardCharsets;

public class UniversalDeserializer<T> implements Deserializer<T> {

    private final ObjectMapper objectMapper;

    public UniversalDeserializer() {
        this(new FastJackson().getObjectMapper());
    }

    public UniversalDeserializer(FastJackson fastJackson) {
        this(fastJackson.getObjectMapper());
    }

    public UniversalDeserializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override public T deserialize(String topic, Headers headers, byte[] data) {
        return deserialize(topic, data);
    }

    @Override
    public T deserialize(String topic, byte[] data) {
        try {
            if (ArrayUtils.isEmpty(data)) return null;

            String wrapperJson = new String(data, StandardCharsets.UTF_8);

            JsonNode wrapperNode = objectMapper.readTree(wrapperJson);
            JsonNode typeNode = wrapperNode.get("type");
            JsonNode dataNode = wrapperNode.get("data");

            if (dataNode.isNull()) return null;

            return objectMapper.treeToValue(dataNode, (Class<T>) Class.forName(typeNode.asText()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
