package com.berzantas.kafkaproducer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.Instant;

public record CoinPriceDto(
        @JsonProperty("usd") BigDecimal priceUsd,
        @JsonProperty("usd_24h_vol") BigDecimal volume24h,
        @JsonProperty("last_updated_at") Instant timestamp
) {}
