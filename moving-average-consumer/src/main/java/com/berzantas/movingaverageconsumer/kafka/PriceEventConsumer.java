package com.berzantas.movingaverageconsumer.kafka;

import com.berzantas.movingaverageconsumer.domain.MovingAverageResult;
import com.berzantas.movingaverageconsumer.domain.PriceEvent;
import com.berzantas.movingaverageconsumer.domain.MovingAverage;
import com.berzantas.movingaverageconsumer.repository.MovingAverageRepository;
import com.berzantas.movingaverageconsumer.service.MovingAverageCalculator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Slf4j
@Component
@RequiredArgsConstructor
public class PriceEventConsumer {

    private final MovingAverageCalculator calculator;
    private final MovingAverageRepository repository;

    @KafkaListener(
            topics = "${app.kafka.topics.crypto-prices.name}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onPriceEvent(
            @Payload PriceEvent event,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            Acknowledgment ack
    ) {
        log.debug("Received event: symbol={}, price={}, partition={}, offset={}, key={}",
                event.symbol(), event.priceUsd(), partition, offset, key);

        try {
            MovingAverageResult result = calculator.addPrice(
                    event.symbol(),
                    event.priceUsd()
            );

            MovingAverage entity = new MovingAverage(
                    result.symbol(),
                    result.average(),
                    result.windowSize(),
                    result.sampleCount(),
                    OffsetDateTime.now(ZoneOffset.UTC)
            );

            repository.save(entity);

            log.info("Processed {}: avg={} (samples={}/{}) [partition={}, offset={}]",
                    result.symbol(), result.average(),
                    result.sampleCount(), result.windowSize(),
                    partition, offset);

            ack.acknowledge();

        } catch (Exception e) {
            log.error("Failed to process event from partition={}, offset={}, symbol={}",
                    partition, offset, event.symbol(), e);
            throw e;
        }
    }
}
