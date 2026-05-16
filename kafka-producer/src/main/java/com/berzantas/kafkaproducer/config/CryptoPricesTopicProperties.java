package com.berzantas.kafkaproducer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.kafka.topics.crypto-prices")
public record CryptoPricesTopicProperties(
        String name,
        int partitions,
        int replicationFactor
) {}
