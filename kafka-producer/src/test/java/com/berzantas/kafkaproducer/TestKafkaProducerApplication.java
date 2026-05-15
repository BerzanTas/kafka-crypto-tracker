package com.berzantas.kafkaproducer;

import org.springframework.boot.SpringApplication;

public class TestKafkaProducerApplication {

    public static void main(String[] args) {
        SpringApplication.from(KafkaProducerApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
