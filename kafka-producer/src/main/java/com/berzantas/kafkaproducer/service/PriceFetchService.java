package com.berzantas.kafkaproducer.service;

import com.berzantas.kafkaproducer.model.PriceEvent;
import com.berzantas.kafkaproducer.webclient.ApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PriceFetchService {

    private final KafkaService kafkaService;
    private final ApiClient apiClient;

    @Scheduled(fixedDelayString = "${app.api.fetch-interval-ms}")
    public void fetchAndPublish() {
        List<PriceEvent> priceEvents = apiClient.getPriceEvents();
        priceEvents.forEach(kafkaService::sendMessage);
        log.info("Price events sent to topic");
    }
}
