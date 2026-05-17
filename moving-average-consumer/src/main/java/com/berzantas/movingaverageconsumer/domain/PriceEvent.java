package com.berzantas.movingaverageconsumer.domain;

import java.math.BigDecimal;
import java.time.Instant;

public record PriceEvent(
        String symbol,
        BigDecimal priceUsd,
        Instant timestamp,
        BigDecimal volume24h
) {}
