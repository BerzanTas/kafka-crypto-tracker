package com.berzantas.kafkaproducer.service;

import com.berzantas.kafkaproducer.config.CryptoPricesTopicProperties;
import com.berzantas.kafkaproducer.model.PriceEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService {

    private final CryptoPricesTopicProperties topicProps;
    private final KafkaTemplate<String, PriceEvent> kafkaTemplate;

    public void sendMessage(PriceEvent priceEvent) {
        kafkaTemplate.send(topicProps.name(), priceEvent.symbol(), priceEvent)
                .whenComplete((result, ex) -> {
                    if(ex != null) {
                        log.error("Failed to send PriceEvent for {}", priceEvent.symbol(), ex);
                    } else {
                        var meta = result.getRecordMetadata();
                        log.info("Sent {} -> partition={}, offset={}",
                                priceEvent.symbol(),
                                meta.partition(),
                                meta.offset());
                    }
                });
    }
}
