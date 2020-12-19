package com.splendidyeti.kafka.client;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.Topology;

import java.util.function.Consumer;

@RequiredArgsConstructor
public class KafkaClients {

    private final KafkaConfigFactory kafkaConfigFactory;

    //
    // Producer
    //

    public <K,V> KafkaProducer<K,V> createProducer(KafkaConfig kafkaConfig) {
        return new KafkaProducer<>(kafkaConfig.getProperties());
    }

    public <K,V> KafkaProducer<K,V> createProducer() {
        return createProducer(kafkaConfigFactory.defaultProducerConfiguration());
    }

    public <K,V> KafkaProducer<K,V> createProducer(Consumer<KafkaConfig> configUpdater) {
        return createProducer(kafkaConfigFactory.defaultProducerConfiguration().update(configUpdater));
    }

    //
    // Consumer
    //

    public <K,V> KafkaConsumer<K,V> createConsumer(KafkaConfig kafkaConfig) {
        return new KafkaConsumer<>(kafkaConfig.getProperties());
    }

    public <K,V> KafkaConsumer<K,V> createConsumer(String groupId) {
        return createConsumer(kafkaConfigFactory.defaultConsumerConfiguration(groupId));
    }

    public <K,V> KafkaConsumer<K,V> createConsumer(String groupId, Consumer<KafkaConfig> configUpdater) {
        return createConsumer(kafkaConfigFactory.defaultConsumerConfiguration(groupId).update(configUpdater));
    }

    //
    // Admin
    //

    public AdminClient createAdminClient(KafkaConfig kafkaConfig) {
        return AdminClient.create(kafkaConfig.getProperties());
    }

    public AdminClient createAdminClient() {
        return createAdminClient(kafkaConfigFactory.defaultAdminConfiguration());
    }

    public AdminClient createAdminClient(Consumer<KafkaConfig> configUpdater) {
        return createAdminClient(kafkaConfigFactory.defaultAdminConfiguration().update(configUpdater));
    }

    //
    // Streams
    //

    public KafkaStreams createStreams(Topology topology, KafkaConfig kafkaConfig) {
        return new KafkaStreams(topology, kafkaConfig.getProperties());
    }

    public KafkaStreams createStreams(Topology topology) {
        return createStreams(topology, kafkaConfigFactory.defaultStreamsConfiguration());
    }

    public KafkaStreams createStreams(Topology topology, Consumer<KafkaConfig> configUpdater) {
        return createStreams(topology, kafkaConfigFactory.defaultStreamsConfiguration().update(configUpdater));
    }
}
