package com.berzantas.kafkaproducer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableConfigurationProperties(CryptoPricesTopicProperties.class)
public class KafkaTopicConfig {

    @Bean
    public NewTopic newTopic(CryptoPricesTopicProperties props) {
        return TopicBuilder.name(props.name())
                .partitions(props.partitions())
                .replicas(props.replicationFactor())
                .build();
    }
}
