package com.splendidyeti.kafka.client;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class KafkaAdminUtils {

    private final AdminClient adminClient;

    public KafkaAdminUtils(KafkaClients kafkaClients) {
        adminClient = kafkaClients.createAdminClient();
    }

    public Set<String> getTopics() {
        try {
            return adminClient.listTopics().names().get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void createTopic(String topic, int numPartitions) {
        createTopics(Arrays.asList(topic), numPartitions);
    }

    public void createTopics(Collection<String> topics, int numPartitions) {
        Set<String> requestedTopics = new HashSet<>(topics);
        requestedTopics.removeAll(getTopics());

        if (!requestedTopics.isEmpty()) {
            List<NewTopic> newTopics = requestedTopics.stream().map(topic -> new NewTopic(topic, numPartitions, (short) 1)).collect(Collectors.toList());
            adminClient.createTopics(newTopics);
        }
    }
}
