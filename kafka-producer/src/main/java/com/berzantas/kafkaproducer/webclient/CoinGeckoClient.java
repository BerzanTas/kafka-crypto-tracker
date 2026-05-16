package com.berzantas.kafkaproducer.webclient;

import com.berzantas.kafkaproducer.model.CoinPriceDto;
import com.berzantas.kafkaproducer.model.PriceEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CoinGeckoClient implements ApiClient{

    @Value("${app.api.base-url}")
    private String apiUrl;
    private static final String RESOURCE =
            "/simple/price?ids=bitcoin,ethereum,solana&vs_currencies=usd&include_24hr_vol=true&include_last_updated_at=true";
    private static final ParameterizedTypeReference<Map<String, CoinPriceDto>> RESPONSE_TYPE =
            new ParameterizedTypeReference<>() {};


    private final RestClient restClient;

    @Override
    public List<PriceEvent> getPriceEvents() {
        Map<String, CoinPriceDto> response = restClient.get()
                .uri(apiUrl + RESOURCE)
                .retrieve()
                .body(RESPONSE_TYPE);

        if(response == null) {
            return List.of();
        }

        return response.entrySet().stream()
                .map(entry ->
                        new PriceEvent(
                                entry.getKey(),
                                entry.getValue().priceUsd(),
                                entry.getValue().timestamp(),
                                entry.getValue().volume24h()
                        ))
                .toList();
    }
}
