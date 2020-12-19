package com.splendidyeti.kafka.client;

@SuppressWarnings("unused")
public interface KafkaConfigConstants {

    //
    // Common Configuration (Producer, Consumer, Admin, Streams)
    //
    String BOOTSTRAP_SERVERS = "bootstrap.servers";
    String CLIENT_ID = "client.id";
    String CONNECTIONS_MAX_IDLE_MS = "connections.max.idle.ms";
    String METADATA_MAX_AGE_MS = "metadata.max.age.ms";
    String METRIC_REPORTERS = "metric.reporters";
    String METRICS_NUM_SAMPLES = "metrics.num.samples";
    String METRICS_RECORDING_LEVEL = "metrics.recording.level";
    String METRICS_SAMPLE_WINDOW_MS = "metrics.sample.window.ms";
    String RECEIVE_BUFFER_BYTES = "receive.buffer.bytes";
    String RECONNECT_BACKOFF_MAX_MS = "reconnect.backoff.max.ms";
    String RECONNECT_BACKOFF_MS = "reconnect.backoff.ms";
    String REQUEST_TIMEOUT_MS = "request.timeout.ms";
    String RETRY_BACKOFF_MS = "retry.backoff.ms";
    String SECURITY_PROTOCOL = "security.protocol";
    String SEND_BUFFER_BYTES = "send.buffer.bytes";

    //
    // Common Configuration (Producer, Consumer, Admin)
    //
    String CLIENT_DNS_LOOKUP = "client.dns.lookup";
    String SASL_CLIENT_CALLBACK_HANDLER_CLASS = "sasl.client.callback.handler.class";
    String SASL_JAAS_CONFIG = "sasl.jaas.config";
    String SASL_KERBEROS_KINIT_CMD = "sasl.kerberos.kinit.cmd";
    String SASL_KERBEROS_MIN_TIME_BEFORE_RELOGIN = "sasl.kerberos.min.time.before.relogin";
    String SASL_KERBEROS_SERVICE_NAME = "sasl.kerberos.service.name";
    String SASL_KERBEROS_TICKET_RENEW_JITTER = "sasl.kerberos.ticket.renew.jitter";
    String SASL_KERBEROS_TICKET_RENEW_WINDOW_FACTOR = "sasl.kerberos.ticket.renew.window.factor";
    String SASL_LOGIN_CALLBACK_HANDLER_CLASS = "sasl.login.callback.handler.class";
    String SASL_LOGIN_CLASS = "sasl.login.class";
    String SASL_LOGIN_REFRESH_BUFFER_SECONDS = "sasl.login.refresh.buffer.seconds";
    String SASL_LOGIN_REFRESH_MIN_PERIOD_SECONDS = "sasl.login.refresh.min.period.seconds";
    String SASL_LOGIN_REFRESH_WINDOW_FACTOR = "sasl.login.refresh.window.factor";
    String SASL_LOGIN_REFRESH_WINDOW_JITTER = "sasl.login.refresh.window.jitter";
    String SASL_MECHANISM = "sasl.mechanism";
    String SECURITY_PROVIDERS = "security.providers";
    String SSL_CIPHER_SUITES = "ssl.cipher.suites";
    String SSL_ENABLED_PROTOCOLS = "ssl.enabled.protocols";
    String SSL_ENDPOINT_IDENTIFICATION_ALGORITHM = "ssl.endpoint.identification.algorithm";
    String SSL_KEY_PASSWORD = "ssl.key.password";
    String SSL_KEYMANAGER_ALGORITHM = "ssl.keymanager.algorithm";
    String SSL_KEYSTORE_LOCATION = "ssl.keystore.location";
    String SSL_KEYSTORE_PASSWORD = "ssl.keystore.password";
    String SSL_KEYSTORE_TYPE = "ssl.keystore.type";
    String SSL_PROTOCOL = "ssl.protocol";
    String SSL_PROVIDER = "ssl.provider";
    String SSL_SECURE_RANDOM_IMPLEMENTATION = "ssl.secure.random.implementation";
    String SSL_TRUSTMANAGER_ALGORITHM = "ssl.trustmanager.algorithm";
    String SSL_TRUSTSTORE_LOCATION = "ssl.truststore.location";
    String SSL_TRUSTSTORE_PASSWORD = "ssl.truststore.password";
    String SSL_TRUSTSTORE_TYPE = "ssl.truststore.type";

    //
    // Common Configuration (Producer, Admin, Streams)
    //
    String RETRIES = "retries";

    //
    // Common Configuration (Producer, Consumer)
    //
    String INTERCEPTOR_CLASSES = "interceptor.classes";

    //
    // Producer Configuration
    //
    String ACKS = "acks";
    String BATCH_SIZE = "batch.size";
    String BUFFER_MEMORY = "buffer.memory";
    String COMPRESSION_TYPE = "compression.type";
    String DELIVERY_TIMEOUT_MS = "delivery.timeout.ms";
    String ENABLE_IDEMPOTENCE = "enable.idempotence";
    String KEY_SERIALIZER = "key.serializer";
    String LINGER_MS = "linger.ms";
    String MAX_BLOCK_MS = "max.block.ms";
    String MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION = "max.in.flight.requests.per.connection";
    String MAX_REQUEST_SIZE = "max.request.size";
    String PARTITIONER_CLASS = "partitioner.class";
    String TRANSACTION_TIMEOUT_MS = "transaction.timeout.ms";
    String TRANSACTIONAL_ID = "transactional.id";
    String VALUE_SERIALIZER = "value.serializer";

    //
    // Consumer Configuration
    //
    String ALLOW_AUTO_CREATE_TOPICS = "allow.auto.create.topics";
    String AUTO_COMMIT_INTERVAL_MS = "auto.commit.interval.ms";
    String AUTO_OFFSET_RESET = "auto.offset.reset";
    String CHECK_CRCS = "check.crcs";
    String CLIENT_RACK = "client.rack";
    String DEFAULT_API_TIMEOUT_MS = "default.api.timeout.ms";
    String ENABLE_AUTO_COMMIT = "enable.auto.commit";
    String EXCLUDE_INTERNAL_TOPICS = "exclude.internal.topics";
    String FETCH_MAX_BYTES = "fetch.max.bytes";
    String FETCH_MAX_WAIT_MS = "fetch.max.wait.ms";
    String FETCH_MIN_BYTES = "fetch.min.bytes";
    String GROUP_ID = "group.id";
    String GROUP_INSTANCE_ID = "group.instance.id";
    String HEARTBEAT_INTERVAL_MS = "heartbeat.interval.ms";
    String ISOLATION_LEVEL = "isolation.level";
    String KEY_DESERIALIZER = "key.deserializer";
    String MAX_PARTITION_FETCH_BYTES = "max.partition.fetch.bytes";
    String MAX_POLL_INTERVAL_MS = "max.poll.interval.ms";
    String MAX_POLL_RECORDS = "max.poll.records";
    String PARTITION_ASSIGNMENT_STRATEGY = "partition.assignment.strategy";
    String SESSION_TIMEOUT_MS = "session.timeout.ms";
    String VALUE_DESERIALIZER = "value.deserializer";

    //
    // Streams Configuration
    //
    String APPLICATION_ID = "application.id";
    String APPLICATION_SERVER = "application.server";
    String BUFFERED_RECORDS_PER_PARTITION = "buffered.records.per.partition";
    String CACHE_MAX_BYTES_BUFFERING = "cache.max.bytes.buffering";
    String COMMIT_INTERVAL_MS = "commit.interval.ms";
    String DEFAULT_KEY_SERDE = "default.key.serde";
    String DEFAULT_PRODUCTION_EXCEPTION_HANDLER = "default.production.exception.handler";
    String DEFAULT_SERIALIZATION_EXCEPTION_HANDLER = "default.deserialization.exception.handler";
    String DEFAULT_TIMESTAMP_EXTRACTOR = "default.timestamp.extractor";
    String DEFAULT_VALUE_SERDE = "default.value.serde";
    String MAX_TASK_IDLE_MS = "max.task.idle.ms";
    String NUM_STANDBY_REPLICAS = "num.standby.replicas";
    String NUM_STREAM_THREADS = "num.stream.threads";
    String PARTITION_GROUPER = "partition.grouper";
    String POLL_MS = "poll.ms";
    String PROCESSING_GUARANTEE = "processing.guarantee";
    String REPLICATION_FACTOR = "replication.factor";
    String ROCKSDB_CONFIG_SETTER = "rocksdb.config.setter";
    String STATE_CLEANUP_DELAY_MS = "state.cleanup.delay.ms";
    String STATE_DIR = "state.dir";
    String TOPOLOGY_OPTIMIZATION = "topology.optimization";
    String UPGRADE_FROM = "upgrade.from";
    String WINDOWSTORE_CHANGELOG_ADDITIONAL_RETENTION_MS = "windowstore.changelog.additional.retention.ms";
}
