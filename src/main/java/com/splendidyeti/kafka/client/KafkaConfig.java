package com.splendidyeti.kafka.client;

import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.kafka.clients.consumer.ConsumerPartitionAssignor;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.metrics.MetricsReporter;
import org.apache.kafka.common.security.auth.AuthenticateCallbackHandler;
import org.apache.kafka.common.security.auth.Login;
import org.apache.kafka.common.security.auth.SecurityProviderCreator;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.streams.errors.DeserializationExceptionHandler;
import org.apache.kafka.streams.errors.ProductionExceptionHandler;
import org.apache.kafka.streams.processor.PartitionGrouper;
import org.apache.kafka.streams.processor.TimestampExtractor;
import org.apache.kafka.streams.state.RocksDBConfigSetter;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@ToString
public class KafkaConfig implements KafkaConfigConstants {

    private final Properties properties = new Properties();

    public KafkaConfig() {
    }

    public KafkaConfig(Properties properties) {
        this.properties.putAll(properties);
    }

    public Properties getProperties() {
        return properties;
    }

    public KafkaConfig update(Consumer<KafkaConfig> configUpdater) {
        configUpdater.accept(this);
        return this;
    }

    //
    // Property accessors
    //

    private String getProperty(String key) {
        return properties.getProperty(key);
    }

    private Boolean getBooleanProperty(String key) {
        return BooleanUtils.toBooleanObject(getProperty(key));
    }

    private <T> Class<? extends T> getClassProperty(String key) {
        return classForName(getProperty(key));
    }

    private <T> List<Class<? extends T>> getClassListProperty(String key) {
        String classListString = getProperty(key);
        if (classListString == null) return null;
        return Arrays.stream(classListString.split(","))
                .map(item -> (Class<? extends T>) classForName(item))
                .collect(Collectors.toList());
    }

    private <T> Class<T> classForName(String className) {
        try {
            return className == null ? null : (Class<T>) Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Double getDoubleProperty(String key) { return NumberUtils.createDouble(getProperty(key)); }

    private Integer getIntegerProperty(String key) {
        return NumberUtils.createInteger(getProperty(key));
    }

    private Long getLongProperty(String key) {
        return NumberUtils.createLong(getProperty(key));
    }

    private Short getShortProperty(String key) {
        String stringValue = getProperty(key);
        return stringValue == null ? null : Short.decode(stringValue);
    }

    private List<String> getStringListProperty(String key) {
        String stringListString = getProperty(key);
        if (stringListString == null) return null;
        return Arrays.asList(stringListString.split(","));
    }

    //
    // Property mutators
    //

    private KafkaConfig setProperty(String key, String value) {
        if (key == null) {
            properties.remove(key);
        } else {
            properties.setProperty(key, value);
        }
        return this;
    }

    private <T> KafkaConfig setProperty(String key, Class<T> value) {
        return setProperty(key, value == null ? null : value.getName());
    }

    private KafkaConfig setProperty(String key, Object value) {
        return setProperty(key, value == null ? null : value.toString());
    }

    private KafkaConfig setStringListProperty(String key, List<String> stringList) {
        String value = null;
        if (!CollectionUtils.isEmpty(stringList)) {
            value = StringUtils.join(stringList, ",");
        }
        return setProperty(key, value);
    }

    private <T> KafkaConfig setClassListProperty(String key, List<Class<? extends T>> classList) {
        String value = null;
        if (!CollectionUtils.isEmpty(classList)) {
            value = StringUtils.join(classList.stream().map(clazz -> clazz.getName()).iterator(), ",");
        }
        return setProperty(key, value);
    }

    //
    // Object accessors/mutators
    //

    public String getAcks() {
        return getProperty(ACKS);
    }

    public KafkaConfig setAcks(String acks) {
        return setProperty(ACKS, acks);
    }

    public Boolean getAllowAutoCreateTopics() {
        return getBooleanProperty(ALLOW_AUTO_CREATE_TOPICS);
    }

    public KafkaConfig setAllowAutoCreateTopics(Boolean allowAutoCreateTopics) {
        return setProperty(ALLOW_AUTO_CREATE_TOPICS, allowAutoCreateTopics);
    }

    public String getApplicationId() {
        return getProperty(APPLICATION_ID);
    }

    public KafkaConfig setApplicationId(String applicationId) {
        return setProperty(APPLICATION_ID, applicationId);
    }

    public String getApplicationServer() {
        return getProperty(APPLICATION_SERVER);
    }

    public KafkaConfig setApplicationServer(String applicationServer) {
        return setProperty(APPLICATION_SERVER, applicationServer);
    }

    public Integer getAutoCommitIntervalMs() {
        return getIntegerProperty(AUTO_COMMIT_INTERVAL_MS);
    }

    public KafkaConfig setAutoCommitIntervalMs(Integer autoCommitIntervalMs) {
        return setProperty(AUTO_COMMIT_INTERVAL_MS, autoCommitIntervalMs);
    }

    public String getAutoOffsetReset() {
        return getProperty(AUTO_OFFSET_RESET);
    }

    public KafkaConfig setAutoOffsetReset(String autoOffsetReset) {
        return setProperty(AUTO_OFFSET_RESET, autoOffsetReset);
    }

    public Integer getBatchSize() {
        return getIntegerProperty(BATCH_SIZE);
    }

    public KafkaConfig setBatchSize(Integer batchSize) {
        return setProperty(BATCH_SIZE, batchSize);
    }

    public String getBootstrapServers() {
        return getProperty(BOOTSTRAP_SERVERS);
    }

    public KafkaConfig setBootstrapServers(String bootstrapServers) {
        return setProperty(BOOTSTRAP_SERVERS, bootstrapServers);
    }

    public Long getBufferMemory() {
        return getLongProperty(BUFFER_MEMORY);
    }

    public KafkaConfig setBufferMemory(Long bufferMemory) {
        return setProperty(BUFFER_MEMORY, bufferMemory);
    }

    public Integer getBufferedRecordsPerPartition() {
        return getIntegerProperty(BUFFERED_RECORDS_PER_PARTITION);
    }

    public KafkaConfig setBufferedRecordsPerPartition(Integer bufferedRecordsPerPartition) {
        return setProperty(BUFFERED_RECORDS_PER_PARTITION, bufferedRecordsPerPartition);
    }

    public Long getCacheMaxBytesBuffering() {
        return getLongProperty(CACHE_MAX_BYTES_BUFFERING);
    }

    public KafkaConfig setCacheMaxBytesBuffering(Long cacheMaxBytesBuffering) {
        return setProperty(CACHE_MAX_BYTES_BUFFERING, cacheMaxBytesBuffering);
    }

    public Boolean getCheckCrcs() {
        return getBooleanProperty(CHECK_CRCS);
    }

    public KafkaConfig setCheckCrcs(Boolean checkCrcs) {
        return setProperty(CHECK_CRCS, checkCrcs);
    }

    public String getClientDnsLookup() {
        return getProperty(CLIENT_DNS_LOOKUP);
    }

    public KafkaConfig setClientDnsLookup(String clientDnsLookup) {
        return setProperty(CLIENT_DNS_LOOKUP, clientDnsLookup);
    }

    public String getClientId() {
        return getProperty(CLIENT_ID);
    }

    public KafkaConfig setClientId(String clientId) {
        return setProperty(CLIENT_ID, clientId);
    }

    public String getClientRack() {
        return getProperty(CLIENT_RACK);
    }

    public KafkaConfig setClientRack(String clientRack) {
        return setProperty(CLIENT_RACK, clientRack);
    }

    public Long getCommitIntervalMs() {
        return getLongProperty(COMMIT_INTERVAL_MS);
    }

    public KafkaConfig setCommitIntervalMs(Long commitIntervalMs) {
        return setProperty(COMMIT_INTERVAL_MS, commitIntervalMs);
    }

    public String getCompressionType() {
        return getProperty(COMPRESSION_TYPE);
    }

    public KafkaConfig setCompressionType(String compressionType) {
        return setProperty(COMPRESSION_TYPE, compressionType);
    }

    public Long getConnectionMaxIdleMs() {
        return getLongProperty(CONNECTIONS_MAX_IDLE_MS);
    }

    public KafkaConfig setConnectionMaxIdleMs(Long connectionMaxIdleMs) {
        return setProperty(CONNECTIONS_MAX_IDLE_MS, connectionMaxIdleMs);
    }

    public Integer getDefaultApiTimeoutMs() {
        return getIntegerProperty(DEFAULT_API_TIMEOUT_MS);
    }

    public KafkaConfig setDefaultApiTimeoutMs(Integer defaultApiTimeoutMs) {
        return setProperty(DEFAULT_API_TIMEOUT_MS, defaultApiTimeoutMs);
    }

    public Class<? extends Serde> getDefaultKeySerde() {
        return getClassProperty(DEFAULT_KEY_SERDE);
    }

    public KafkaConfig setDefaultKeySerde(Class<? extends Serde> defaultKeySerde) {
        return setProperty(DEFAULT_KEY_SERDE, defaultKeySerde);
    }

    public Class<? extends ProductionExceptionHandler> getDefaultProductionExceptionHandler() {
        return getClassProperty(DEFAULT_PRODUCTION_EXCEPTION_HANDLER);
    }

    public KafkaConfig setDefaultProductionExceptionHandler(Class<? extends ProductionExceptionHandler> defaultProductionExceptionHandler) {
        return setProperty(DEFAULT_PRODUCTION_EXCEPTION_HANDLER, defaultProductionExceptionHandler);
    }

    public Class<? extends DeserializationExceptionHandler> getDefaultSerializationExceptionHandler() {
        return getClassProperty(DEFAULT_SERIALIZATION_EXCEPTION_HANDLER);
    }

    public KafkaConfig setDefaultSerializationExceptionHandler(Class<? extends DeserializationExceptionHandler> defaultSerializationExceptionHandler) {
        return setProperty(DEFAULT_SERIALIZATION_EXCEPTION_HANDLER, defaultSerializationExceptionHandler);
    }

    public Class<? extends TimestampExtractor> getDefaultTimestampExtractor() {
        return getClassProperty(DEFAULT_TIMESTAMP_EXTRACTOR);
    }

    public KafkaConfig setDefaultTimestampExtractor(Class<? extends TimestampExtractor> defaultTimestampExtractor) {
        return setProperty(DEFAULT_TIMESTAMP_EXTRACTOR, defaultTimestampExtractor);
    }

    public Class<? extends Serde> getDefaultValueSerde() {
        return getClassProperty(DEFAULT_VALUE_SERDE);
    }

    public KafkaConfig setDefaultValueSerde(Class<? extends Serde> defaultValueSerde) {
        return setProperty(DEFAULT_VALUE_SERDE, defaultValueSerde);
    }

    public Integer getDeliveryTimeoutMs() {
        return getIntegerProperty(DELIVERY_TIMEOUT_MS);
    }

    public KafkaConfig setDeliveryTimeoutMs(Integer deliveryTimeoutMs) {
        return setProperty(DELIVERY_TIMEOUT_MS, deliveryTimeoutMs);
    }

    public Boolean getEnableAutoCommit() {
        return getBooleanProperty(ENABLE_AUTO_COMMIT);
    }

    public KafkaConfig setEnableAutoCommit(Boolean enableAutoCommit) {
        return setProperty(ENABLE_AUTO_COMMIT, enableAutoCommit);
    }

    public Boolean getEnableIdempotence() {
        return getBooleanProperty(ENABLE_IDEMPOTENCE);
    }

    public KafkaConfig setEnableIdempotence(Boolean enableIdempotence) {
        return setProperty(ENABLE_IDEMPOTENCE, enableIdempotence);
    }

    public Boolean getExcludeInternalTopics() {
        return getBooleanProperty(EXCLUDE_INTERNAL_TOPICS);
    }

    public KafkaConfig setExcludeInternalTopics(Boolean excludeInternalTopics) {
        return setProperty(EXCLUDE_INTERNAL_TOPICS, excludeInternalTopics);
    }

    public Integer getFetchMaxBytes() {
        return getIntegerProperty(FETCH_MAX_BYTES);
    }

    public KafkaConfig setFetchMaxBytes(Integer fetchMaxBytes) {
        return setProperty(FETCH_MAX_BYTES, fetchMaxBytes);
    }

    public Integer getFetchMaxWaitMs() {
        return getIntegerProperty(FETCH_MAX_WAIT_MS);
    }

    public KafkaConfig setFetchMaxWaitMs(Integer fetchMaxWaitMs) {
        return setProperty(FETCH_MAX_WAIT_MS, fetchMaxWaitMs);
    }

    public Integer getFetchMinBytes() {
        return getIntegerProperty(FETCH_MIN_BYTES);
    }

    public KafkaConfig setFetchMinBytes(Integer fetchMinBytes) {
        return setProperty(FETCH_MIN_BYTES, fetchMinBytes);
    }

    public String getGroupId() {
        return getProperty(GROUP_ID);
    }

    public KafkaConfig setGroupId(String groupId) {
        return setProperty(GROUP_ID, groupId);
    }

    public String getGroupInstanceId() {
        return getProperty(GROUP_INSTANCE_ID);
    }

    public KafkaConfig setGroupInstanceId(String groupInstanceId) {
        return setProperty(GROUP_INSTANCE_ID, groupInstanceId);
    }

    public Integer getHeartbeatIntervalMs() {
        return getIntegerProperty(HEARTBEAT_INTERVAL_MS);
    }

    public KafkaConfig setHeartbeatIntervalMs(Integer heartbeatIntervalMs) {
        return setProperty(HEARTBEAT_INTERVAL_MS, heartbeatIntervalMs);
    }

    public String getIsolationLevel() {
        return getProperty(ISOLATION_LEVEL);
    }

    public KafkaConfig setIsolationLevel(String isolationLevel) {
        return setProperty(ISOLATION_LEVEL, isolationLevel);
    }

    public Class<? extends Deserializer> getKeyDeserializer() {
        return getClassProperty(KEY_DESERIALIZER);
    }

    public KafkaConfig setKeyDeserializer(Class<? extends Deserializer> keyDeserializer) {
        return setProperty(KEY_DESERIALIZER, keyDeserializer);
    }

    public Class<? extends Serializer> getKeySerializer() {
        return getClassProperty(KEY_SERIALIZER);
    }

    public KafkaConfig setKeySerializer(Class<? extends Serializer> keySerializer) {
        return setProperty(KEY_SERIALIZER, keySerializer);
    }

    public Long getLingerMs() {
        return getLongProperty(LINGER_MS);
    }

    public KafkaConfig setLingerMs(Long lingerMs) {
        return setProperty(LINGER_MS, lingerMs);
    }

    public Long getMaxBlockMs() {
        return getLongProperty(MAX_BLOCK_MS);
    }

    public KafkaConfig setMaxBlockMs(Long maxBlockMs) {
        return setProperty(MAX_BLOCK_MS, maxBlockMs);
    }

    public Integer getMaxInFlightRequestsPerConnection() {
        return getIntegerProperty(MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION);
    }

    public KafkaConfig setMaxInFlightRequestsPerConnection(Integer maxInFlightRequestsPerConnection) {
        return setProperty(MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, maxInFlightRequestsPerConnection);
    }

    public Integer getMaxPartitionFetchBytes() {
        return getIntegerProperty(MAX_PARTITION_FETCH_BYTES);
    }

    public KafkaConfig setMaxPartitionFetchBytes(Integer maxPartitionFetchBytes) {
        return setProperty(MAX_PARTITION_FETCH_BYTES, maxPartitionFetchBytes);
    }

    public Integer getMaxPollIntervalMs() {
        return getIntegerProperty(MAX_POLL_INTERVAL_MS);
    }

    public KafkaConfig setMaxPollIntervalMs(Integer maxPollIntervalMs) {
        return setProperty(MAX_POLL_INTERVAL_MS, maxPollIntervalMs);
    }

    public Integer getMaxPollRecords() {
        return getIntegerProperty(MAX_POLL_RECORDS);
    }

    public KafkaConfig setMaxPollRecords(Integer maxPollRecords) {
        return setProperty(MAX_POLL_RECORDS, maxPollRecords);
    }

    public Integer getMaxRequestSize() {
        return getIntegerProperty(MAX_REQUEST_SIZE);
    }

    public KafkaConfig setMaxRequestSize(Integer maxRequestSize) {
        return setProperty(MAX_REQUEST_SIZE, maxRequestSize);
    }

    public Long getMaxTaskIdleMs() {
        return getLongProperty(MAX_TASK_IDLE_MS);
    }

    public KafkaConfig setMaxTaskIdleMs(Long maxTaskIdleMs) {
        return setProperty(MAX_TASK_IDLE_MS , maxTaskIdleMs);
    }

    public Long getMetadataMaxAgeMs() {
        return getLongProperty(METADATA_MAX_AGE_MS);
    }

    public KafkaConfig setMetadataMaxAgeMs(Long metadataMaxAgeMs) {
        return setProperty(METADATA_MAX_AGE_MS, metadataMaxAgeMs);
    }

    public List<Class<? extends MetricsReporter>> getMetricReporters() {
        return getClassListProperty(METRIC_REPORTERS);
    }

    public KafkaConfig setMetricReporters(List<Class<? extends MetricsReporter>> metricReporters) {
        return setClassListProperty(METRIC_REPORTERS, metricReporters);
    }

    public Integer getMetricsNumSamples() {
        return getIntegerProperty(METRICS_NUM_SAMPLES);
    }

    public KafkaConfig setMetricsNumSamples(Integer metricsNumSamples) {
        return setProperty(METRICS_NUM_SAMPLES, metricsNumSamples);
    }

    public String getMetricsRecordingLevel() {
        return getProperty(METRICS_RECORDING_LEVEL);
    }

    public KafkaConfig setMetricsRecordingLevel(String metricsRecordingLevel) {
        return setProperty(METRICS_RECORDING_LEVEL, metricsRecordingLevel);
    }

    public Long getMetricsSampleWindowMs() {
        return getLongProperty(METRICS_SAMPLE_WINDOW_MS);
    }

    public KafkaConfig setMetricsSampleWindowMs(Long metricsSampleWindowMs) {
        return setProperty(METRICS_SAMPLE_WINDOW_MS, metricsSampleWindowMs);
    }

    public Integer getNumStandbyReplicas() {
        return getIntegerProperty(NUM_STANDBY_REPLICAS);
    }

    public KafkaConfig setNumStandbyReplicas(Integer numStandbyReplicas) {
        return setProperty(NUM_STANDBY_REPLICAS, numStandbyReplicas);
    }

    public Integer getNumStreamThreads() {
        return getIntegerProperty(NUM_STREAM_THREADS);
    }

    public KafkaConfig setNumStreamThreads(Integer numStreamThreads) {
        return setProperty(NUM_STREAM_THREADS, numStreamThreads);
    }

    public List<Class<? extends ConsumerPartitionAssignor>> getPartitionAssignmentStrategy() {
        return getClassListProperty(PARTITION_ASSIGNMENT_STRATEGY);
    }

    public KafkaConfig setPartitionAssignmentStrategy(List<Class<? extends ConsumerPartitionAssignor>> partitionAssignmentStrategy) {
        return setClassListProperty(PARTITION_ASSIGNMENT_STRATEGY, partitionAssignmentStrategy);
    }

    public Class<? extends PartitionGrouper> getPartitionGrouper() {
        return getClassProperty(PARTITION_GROUPER);
    }

    public KafkaConfig setPartitionGrouper(Class<? extends PartitionGrouper> partitionGrouper) {
        return setProperty(PARTITION_GROUPER, partitionGrouper);
    }

    public Class<? extends Partitioner> getPartitionerClass() {
        return getClassProperty(PARTITIONER_CLASS);
    }

    public KafkaConfig setPartitionerClass(Class<? extends Partitioner> partitionerClass) {
        return setProperty(PARTITIONER_CLASS, partitionerClass);
    }

    public Long getPollMs() {
        return getLongProperty(POLL_MS);
    }

    public KafkaConfig setPollMs(Long pollMs) {
        return setProperty(POLL_MS, pollMs);
    }

    public String getProcessingGuarantee() {
        return getProperty(PROCESSING_GUARANTEE);
    }

    public KafkaConfig setProcessingGuarantee(String processingGuarantee) {
        return setProperty(PROCESSING_GUARANTEE, processingGuarantee);
    }

    public Integer getReceiveBufferBytes() {
        return getIntegerProperty(RECEIVE_BUFFER_BYTES);
    }

    public KafkaConfig setReceiveBufferBytes(Integer receiveBufferBytes) {
        return setProperty(RECEIVE_BUFFER_BYTES, receiveBufferBytes);
    }

    public Long getReconnectBackoffMaxMs() {
        return getLongProperty(RECONNECT_BACKOFF_MAX_MS);
    }

    public KafkaConfig setReconnectBackoffMaxMs(Long reconnectBackoffMaxMs) {
        return setProperty(RECONNECT_BACKOFF_MAX_MS, reconnectBackoffMaxMs);
    }

    public Long getReconnectBackoffMs() {
        return getLongProperty(RECONNECT_BACKOFF_MS);
    }

    public KafkaConfig setReconnectBackoffMs(Long reconnectBackoffMs) {
        return setProperty(RECONNECT_BACKOFF_MS, reconnectBackoffMs);
    }

    public Integer getReplicationFactor() {
        return getIntegerProperty(REPLICATION_FACTOR);
    }

    public KafkaConfig setReplicationFactor(Integer replicationFactor) {
        return setProperty(REPLICATION_FACTOR, replicationFactor);
    }

    public Integer getRequestTimeoutMs() {
        return getIntegerProperty(REQUEST_TIMEOUT_MS);
    }

    public KafkaConfig setRequestTimeoutMs(Integer requestTimeoutMs) {
        return setProperty(REQUEST_TIMEOUT_MS, requestTimeoutMs);
    }

    public Integer getRetries() {
        return getIntegerProperty(RETRIES);
    }

    public KafkaConfig setRetries(Integer retries) {
        return setProperty(RETRIES, retries);
    }

    public Long getRetryBackoffMs() {
        return getLongProperty(RETRY_BACKOFF_MS);
    }

    public KafkaConfig setRetryBackoffMs(Long retryBackoffMs) {
        return setProperty(RETRY_BACKOFF_MS, retryBackoffMs);
    }

    public Class<? extends RocksDBConfigSetter> getRocksdbConfigSetter() {
        return getClassProperty(ROCKSDB_CONFIG_SETTER);
    }

    public KafkaConfig setRocksdbConfigSetter(Class<? extends RocksDBConfigSetter> rocksdbConfigSetter) {
        return setProperty(ROCKSDB_CONFIG_SETTER, rocksdbConfigSetter);
    }

    public Class<? extends AuthenticateCallbackHandler> getSaslClientCallbackHandlerClass() {
        return getClassProperty(SASL_CLIENT_CALLBACK_HANDLER_CLASS);
    }

    public KafkaConfig setSaslClientCallbackHandlerClass(Class<? extends AuthenticateCallbackHandler> saslClientCallbackHandlerClass) {
        return setProperty(SASL_CLIENT_CALLBACK_HANDLER_CLASS, saslClientCallbackHandlerClass);
    }

    public String getSaslJaasConfig() {
        return getProperty(SASL_JAAS_CONFIG);
    }

    public KafkaConfig setSaslJaasConfig(String saslJaasConfig) {
        return setProperty(SASL_JAAS_CONFIG, saslJaasConfig);
    }

    public String getSaslKerberosKinitCmd() {
        return getProperty(SASL_KERBEROS_KINIT_CMD);
    }

    public KafkaConfig setSaslKerberosKinitCmd(String saslKerberosKinitCmd) {
        return setProperty(SASL_KERBEROS_KINIT_CMD, saslKerberosKinitCmd);
    }

    public Long getSaslKerberosMinTimeBeforeRelogin() {
        return getLongProperty(SASL_KERBEROS_MIN_TIME_BEFORE_RELOGIN);
    }

    public KafkaConfig setSaslKerberosMinTimeBeforeRelogin(Long saslKerberosMinTimeBeforeRelogin) {
        return setProperty(SASL_KERBEROS_MIN_TIME_BEFORE_RELOGIN, saslKerberosMinTimeBeforeRelogin);
    }

    public String getSaslKerberosServiceName() {
        return getProperty(SASL_KERBEROS_SERVICE_NAME);
    }

    public KafkaConfig setSaslKerberosServiceName(String saslKerberosServiceName) {
        return setProperty(SASL_KERBEROS_SERVICE_NAME, saslKerberosServiceName);
    }

    public Double getSaslKerberosTicketRenewJitter() {
        return getDoubleProperty(SASL_KERBEROS_TICKET_RENEW_JITTER);
    }

    public KafkaConfig setSaslKerberosTicketRenewJitter(Double saslKerberosTicketRenewJitter) {
        return setProperty(SASL_KERBEROS_TICKET_RENEW_JITTER, saslKerberosTicketRenewJitter);
    }

    public Double getSaslKerberosTicketRenewWindowFactor() {
        return getDoubleProperty(SASL_KERBEROS_TICKET_RENEW_WINDOW_FACTOR);
    }

    public KafkaConfig setSaslKerberosTicketRenewWindowFactor(Double saslKerberosTicketRenewWindowFactor) {
        return setProperty(SASL_KERBEROS_TICKET_RENEW_WINDOW_FACTOR, saslKerberosTicketRenewWindowFactor);
    }

    public Class<? extends AuthenticateCallbackHandler> getSaslLoginCallbackHandlerClass() {
        return getClassProperty(SASL_LOGIN_CALLBACK_HANDLER_CLASS);
    }

    public KafkaConfig setSaslLoginCallbackHandlerClass(Class<? extends AuthenticateCallbackHandler> saslLoginCallbackHandlerClass) {
        return setProperty(SASL_LOGIN_CALLBACK_HANDLER_CLASS, saslLoginCallbackHandlerClass);
    }

    public Class<? extends Login> getSaslLoginClass() {
        return getClassProperty(SASL_LOGIN_CLASS);
    }

    public KafkaConfig setSaslLoginClass(Class<? extends Login> saslLoginClass) {
        return setProperty(SASL_LOGIN_CLASS, saslLoginClass);
    }

    public Short getSaslLoginRefreshBufferSeconds() {
        return getShortProperty(SASL_LOGIN_REFRESH_BUFFER_SECONDS);
    }

    public KafkaConfig setSaslLoginRefreshBufferSeconds(Short saslLoginRefreshBufferSeconds) {
        return setProperty(SASL_LOGIN_REFRESH_BUFFER_SECONDS, saslLoginRefreshBufferSeconds);
    }

    public Short getSaslLoginRefreshMinPeriodSeconds() {
        return getShortProperty(SASL_LOGIN_REFRESH_MIN_PERIOD_SECONDS);
    }

    public KafkaConfig setSaslLoginRefreshMinPeriodSeconds(Short saslLoginRefreshMinPeriodSeconds) {
        return setProperty(SASL_LOGIN_REFRESH_MIN_PERIOD_SECONDS, saslLoginRefreshMinPeriodSeconds);
    }

    public Double getSaslLoginRefreshWindowFactor() {
        return getDoubleProperty(SASL_LOGIN_REFRESH_WINDOW_FACTOR);
    }

    public KafkaConfig setSaslLoginRefreshWindowFactor(Double saslLoginRefreshWindowFactor) {
        return setProperty(SASL_LOGIN_REFRESH_WINDOW_FACTOR, saslLoginRefreshWindowFactor);
    }

    public Double getSaslLoginRefreshWindowJitter() {
        return getDoubleProperty(SASL_LOGIN_REFRESH_WINDOW_JITTER);
    }

    public KafkaConfig setSaslLoginRefreshWindowJitter(Double saslLoginRefreshWindowJitter) {
        return setProperty(SASL_LOGIN_REFRESH_WINDOW_JITTER, saslLoginRefreshWindowJitter);
    }

    public String getSaslMechanism() {
        return getProperty(SASL_MECHANISM);
    }

    public KafkaConfig setSaslMechanism(String saslMechanism) {
        return setProperty(SASL_MECHANISM, saslMechanism);
    }

    public String getSecurityProtocol() {
        return getProperty(SECURITY_PROTOCOL);
    }

    public KafkaConfig setSecurityProtocol(String securityProtocol) {
        return setProperty(SECURITY_PROTOCOL, securityProtocol);
    }

    public List<Class<? extends SecurityProviderCreator>> getSecurityProviders() {
        return getClassListProperty(SECURITY_PROVIDERS);
    }

    public KafkaConfig setSecurityProviders(List<Class<? extends SecurityProviderCreator>> securityProviders) {
        return setClassListProperty(SECURITY_PROVIDERS, securityProviders);
    }

    public Integer getSendBufferBytes() {
        return getIntegerProperty(SEND_BUFFER_BYTES);
    }

    public KafkaConfig setSendBufferBytes(Integer sendBufferBytes) {
        return setProperty(SEND_BUFFER_BYTES, sendBufferBytes);
    }

    public Integer getSessionTimeoutMs() {
        return getIntegerProperty(SESSION_TIMEOUT_MS);
    }

    public KafkaConfig setSessionTimeoutMs(Integer sessionTimeoutMs) {
        return setProperty(SESSION_TIMEOUT_MS, sessionTimeoutMs);
    }

    public List<String> getSslCipherSuites() {
        return getStringListProperty(SSL_CIPHER_SUITES);
    }

    public KafkaConfig setSslCipherSuites(List<String> sslCipherSuites) {
        return setStringListProperty(SSL_CIPHER_SUITES, sslCipherSuites);
    }

    public List<String> getSslEnabledProtocols() {
        return getStringListProperty(SSL_ENABLED_PROTOCOLS);
    }

    public KafkaConfig setSslEnabledProtocols(List<String> sslEnabledProtocols) {
        return setStringListProperty(SSL_ENABLED_PROTOCOLS, sslEnabledProtocols);
    }

    public String getSslEndpointIdentificationAlgorithm() {
        return getProperty(SSL_ENDPOINT_IDENTIFICATION_ALGORITHM);
    }

    public KafkaConfig setSslEndpointIdentificationAlgorithm(String sslEndpointIdentificationAlgorithm) {
        return setProperty(SSL_ENDPOINT_IDENTIFICATION_ALGORITHM, sslEndpointIdentificationAlgorithm);
    }

    public String getSslKeyPassword() {
        return getProperty(SSL_KEY_PASSWORD);
    }

    public KafkaConfig setSslKeyPassword(String sslKeyPassword) {
        return setProperty(SSL_KEY_PASSWORD, sslKeyPassword);
    }

    public String getSslKeymanagerAlgorithm() {
        return getProperty(SSL_KEYMANAGER_ALGORITHM);
    }

    public KafkaConfig setSslKeymanagerAlgorithm(String sslKeymanagerAlgorithm) {
        return setProperty(SSL_KEYMANAGER_ALGORITHM, sslKeymanagerAlgorithm);
    }

    public String getSslKeystoreLocation() {
        return getProperty(SSL_KEYSTORE_LOCATION);
    }

    public KafkaConfig setSslKeystoreLocation(String sslKeystoreLocation) {
        return setProperty(SSL_KEYSTORE_LOCATION, sslKeystoreLocation);
    }

    public String getSslKeystorePassword() {
        return getProperty(SSL_KEYSTORE_PASSWORD);
    }

    public KafkaConfig setSslKeystorePassword(String sslKeystorePassword) {
        return setProperty(SSL_KEYSTORE_PASSWORD, sslKeystorePassword);
    }

    public String getSslKeystoreType() {
        return getProperty(SSL_KEYSTORE_TYPE);
    }

    public KafkaConfig setSslKeystoreType(String sslKeystoreType) {
        return setProperty(SSL_KEYSTORE_TYPE, sslKeystoreType);
    }

    public String getSslProtocol() {
        return getProperty(SSL_PROTOCOL);
    }

    public KafkaConfig setSslProtocol(String sslProtocol) {
        return setProperty(SSL_PROTOCOL, sslProtocol);
    }

    public String getSslProvider() {
        return getProperty(SSL_PROVIDER);
    }

    public KafkaConfig setSslProvider(String sslProvider) {
        return setProperty(SSL_PROVIDER, sslProvider);
    }

    public String getSslSecureRandomImplementation() {
        return getProperty(SSL_SECURE_RANDOM_IMPLEMENTATION);
    }

    public KafkaConfig setSslSecureRandomImplementation(String sslSecureRandomImplementation) {
        return setProperty(SSL_SECURE_RANDOM_IMPLEMENTATION, sslSecureRandomImplementation);
    }

    public String getSslTrustmanagerAlgorithm() {
        return getProperty(SSL_TRUSTMANAGER_ALGORITHM);
    }

    public KafkaConfig setSslTrustmanagerAlgorithm(String sslTrustmanagerAlgorithm) {
        return setProperty(SSL_TRUSTMANAGER_ALGORITHM, sslTrustmanagerAlgorithm);
    }

    public String getSslTruststoreLocation() {
        return getProperty(SSL_TRUSTSTORE_LOCATION);
    }

    public KafkaConfig setSslTruststoreLocation(String sslTruststoreLocation) {
        return setProperty(SSL_TRUSTSTORE_LOCATION, sslTruststoreLocation);
    }

    public String getSslTruststorePassword() {
        return getProperty(SSL_TRUSTSTORE_PASSWORD);
    }

    public KafkaConfig setSslTruststorePassword(String sslTruststorePassword) {
        return setProperty(SSL_TRUSTSTORE_PASSWORD, sslTruststorePassword);
    }

    public String getSslTruststoreType() {
        return getProperty(SSL_TRUSTSTORE_TYPE);
    }

    public KafkaConfig setSslTruststoreType(String sslTruststoreType) {
        return setProperty(SSL_TRUSTSTORE_TYPE, sslTruststoreType);
    }

    public Long getStateCleanupDelayMs() {
        return getLongProperty(STATE_CLEANUP_DELAY_MS);
    }

    public KafkaConfig setStateCleanupDelayMs(Long stateCleanupDelayMs) {
        return setProperty(STATE_CLEANUP_DELAY_MS, stateCleanupDelayMs);
    }

    public String getStateDir() {
        return getProperty(STATE_DIR);
    }

    public KafkaConfig setStateDir(String stateDir) {
        return setProperty(STATE_DIR, stateDir);
    }

    public String getTopologyOptimization() {
        return getProperty(TOPOLOGY_OPTIMIZATION);
    }

    public KafkaConfig setTopologyOptimization(String topologyOptimization) {
        return setProperty(TOPOLOGY_OPTIMIZATION, topologyOptimization);
    }

    public Integer getTransactionTimeoutMs() {
        return getIntegerProperty(TRANSACTION_TIMEOUT_MS);
    }

    public KafkaConfig setTransactionTimeoutMs(Integer transactionTimeoutMs) {
        return setProperty(TRANSACTION_TIMEOUT_MS, transactionTimeoutMs);
    }

    public String getTransactionalId() {
        return getProperty(TRANSACTIONAL_ID);
    }

    public KafkaConfig setTransactionalId(String transactionalId) {
        return setProperty(TRANSACTIONAL_ID, transactionalId);
    }

    public String getUpgradeFrom() {
        return getProperty(UPGRADE_FROM);
    }

    public KafkaConfig setUpgradeFrom(String upgradeFrom) {
        return setProperty(UPGRADE_FROM, upgradeFrom);
    }

    public Class<? extends Deserializer> getValueDeserializer() {
        return getClassProperty(VALUE_DESERIALIZER);
    }

    public KafkaConfig setValueDeserializer(Class<? extends Deserializer> valueDeserializer) {
        return setProperty(VALUE_DESERIALIZER, valueDeserializer);
    }

    public Class<? extends Serializer> getValueSerializer() {
        return getClassProperty(VALUE_SERIALIZER);
    }

    public KafkaConfig setValueSerializer(Class<? extends Serializer> valueSerializer) {
        return setProperty(VALUE_SERIALIZER, valueSerializer);
    }

    public Long getWindowstoreChangelogAdditionalRetentionMs() {
        return getLongProperty(WINDOWSTORE_CHANGELOG_ADDITIONAL_RETENTION_MS);
    }

    public KafkaConfig setWindowstoreChangelogAdditionalRetentionMs(Long windowstoreChangelogAdditionalRetentionMs) {
        return setProperty(WINDOWSTORE_CHANGELOG_ADDITIONAL_RETENTION_MS, windowstoreChangelogAdditionalRetentionMs);
    }

//    //
//    // Save these property definitions in case it is necessary to regenerate this mess.
//    //
//
//    String acks;
//    Boolean allowAutoCreateTopics;
//    String applicationId;
//    String applicationServer;
//    Integer autoCommitIntervalMs;
//    String autoOffsetReset;
//    Integer batchSize;
//    String bootstrapServers;
//    Long bufferMemory;
//    Integer bufferedRecordsPerPartition;
//    Long cacheMaxBytesBuffering;
//    Boolean checkCrcs;
//    String clientDnsLookup;
//    String clientId;
//    String clientRack;
//    Long commitIntervalMs;
//    String compressionType;
//    Long connectionMaxIdleMs;
//    Integer defaultApiTimeoutMs;
//    Class<? extends Serde> defaultKeySerde;
//    Class<? extends ProductionExceptionHandler> defaultProductionExceptionHandler;
//    Class<? extends DeserializationExceptionHandler> defaultSerializationExceptionHandler;
//    Class<? extends TimestampExtractor> defaultTimestampExtractor;
//    Class<? extends Serde> defaultValueSerde;
//    Integer deliveryTimeoutMs;
//    Boolean enableAutoCommit;
//    Boolean enableIdempotence;
//    Boolean excludeInternalTopics;
//    Integer fetchMaxBytes;
//    Integer fetchMaxWaitMs;
//    Integer fetchMinBytes;
//    String groupId;
//    String groupInstanceId;
//    Integer heartbeatIntervalMs;
//    String isolationLevel;
//    Class<? extends Deserializer> keyDeserializer;
//    Class<? extends Serializer> keySerializer;
//    Long lingerMs;
//    Long maxBlockMs;
//    Integer maxInFlightRequestsPerConnection;
//    Integer maxPartitionFetchBytes;
//    Integer maxPollIntervalMs;
//    Integer maxPollRecords;
//    Integer maxRequestSize;
//    Long maxTaskIdleMs;
//    Long metadataMaxAgeMs;
//    List<Class<? extends MetricsReporter>> metricReporters;
//    Integer metricsNumSamples;
//    String metricsRecordingLevel;
//    Long metricsSampleWindowMs;
//    Integer numStandbyReplicas;
//    Integer numStreamThreads;
//    List<Class<? extends ConsumerPartitionAssignor>> partitionAssignmentStrategy;
//    Class<? extends PartitionGrouper> partitionGrouper;
//    Class<? extends Partitioner> partitionerClass;
//    Long pollMs;
//    String processingGuarantee;
//    Integer receiveBufferBytes;
//    Long reconnectBackoffMaxMs;
//    Long reconnectBackoffMs;
//    Integer replicationFactor;
//    Integer requestTimeoutMs;
//    Long retryBackoffMs;
//    Class<? extends RocksDBConfigSetter> rocksdbConfigSetter;
//    Class<? extends AuthenticateCallbackHandler> saslClientCallbackHandlerClass;
//    String saslJaasConfig;
//    String saslKerberosKinitCmd;
//    Long saslKerberosMinTimeBeforeRelogin;
//    String saslKerberosServiceName;
//    Double saslKerberosTicketRenewJitter;
//    Double saslKerberosTicketRenewWindowFactor;
//    Class<? extends AuthenticateCallbackHandler> saslLoginCallbackHandlerClass;
//    Class<? extends Login> saslLoginClass;
//    Short saslLoginRefreshBufferSeconds;
//    Short saslLoginRefreshMinPeriodSeconds;
//    Double saslLoginRefreshWindowFactor;
//    Double saslLoginRefreshWindowJitter;
//    String saslMechanism;
//    String securityProtocol;
//    List<Class<? extends SecurityProviderCreator>> securityProviders;
//    Integer sendBufferBytes;
//    Integer sessionTimeoutMs;
//    List<String> sslCipherSuites;
//    List<String> sslEnabledProtocols;
//    String sslEndpointIdentificationAlgorithm;
//    String sslKeyPassword;
//    String sslKeymanagerAlgorithm;
//    String sslKeystoreLocation;
//    String sslKeystorePassword;
//    String sslKeystoreType;
//    String sslProtocol;
//    String sslProvider;
//    String sslSecureRandomImplementation;
//    String sslTrustmanagerAlgorithm;
//    String sslTruststoreLocation;
//    String sslTruststorePassword;
//    String sslTruststoreType;
//    Long stateCleanupDelayMs;
//    String stateDir;
//    String topologyOptimization;
//    Integer transactionTimeoutMs;
//    String transactionalId;
//    String upgradeFrom;
//    Class<? extends Deserializer> valueDeserializer;
//    Class<? extends Serializer> valueSerializer;
//    Long windowstoreChangelogAdditionalRetentionMs;

}
