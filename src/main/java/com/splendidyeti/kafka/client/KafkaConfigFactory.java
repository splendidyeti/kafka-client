package com.splendidyeti.kafka.client;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

@Builder
@Value
@With
public class KafkaConfigFactory implements KafkaConfigConstants {

    private final String applicationName;
    private final String bootstrapServers;
    private final String defaultClientId;
    private final String securityProtocol;
    private final String sslEndpointIdentificationAlgorithm;
    private final String streamsDefaultProcessingGuarantee;
    private final int streamsDefaultReplicationFactor;

    public KafkaConfig defaultCommonConfiguration() {
        return new KafkaConfig()
                .setBootstrapServers(bootstrapServers)
                .setSecurityProtocol(securityProtocol)
                .setSslEndpointIdentificationAlgorithm(sslEndpointIdentificationAlgorithm)
                .setClientId(defaultClientId);
    }

    public KafkaConfig defaultProducerConfiguration() {
        return defaultCommonConfiguration()
                .setAcks("all")
                .setEnableIdempotence(true)
                .setKeySerializer(StringSerializer.class)
                .setMaxInFlightRequestsPerConnection(1)
                .setRetries(Integer.MAX_VALUE)
                .setValueSerializer(UniversalSerializer.class);
    }

    public KafkaConfig defaultConsumerConfiguration(String groupId) {
        return defaultCommonConfiguration()
                .setAllowAutoCreateTopics(false)
                .setAutoCommitIntervalMs(1000)
                .setGroupId(groupId)
                .setIsolationLevel("read_committed")
                .setKeyDeserializer(StringDeserializer.class)
                .setValueDeserializer(UniversalDeserializer.class);
    }

    public KafkaConfig defaultAdminConfiguration() {
        return defaultCommonConfiguration()
                .setRetries(Integer.MAX_VALUE);
    }

    public KafkaConfig defaultStreamsConfiguration() {
        return defaultCommonConfiguration()
                .setApplicationId(applicationName)
                .setRetries(Integer.MAX_VALUE)
                .setDefaultKeySerde(Serdes.String().getClass())
                .setDefaultValueSerde(UniversalSerde.class)
                .setProcessingGuarantee(streamsDefaultProcessingGuarantee)
                .setReplicationFactor(streamsDefaultReplicationFactor);
    }
}
