package com.splendidyeti.kafka.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class FastJackson {

    private final ObjectMapper objectMapper;

    public FastJackson() {
        this.objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public FastJackson(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T asObject(String json, Class<T> klass) {
        try {
            return objectMapper.readValue(json, klass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <T> String asString(T data) {
        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }
}
