package com.berzantas.movingaverageconsumer.domain;

import java.math.BigDecimal;

public record MovingAverageResult(
        String symbol,
        BigDecimal average,
        int sampleCount,
        int windowSize
) {}