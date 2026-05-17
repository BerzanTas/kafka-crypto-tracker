package com.berzantas.movingaverageconsumer.service;

import com.berzantas.movingaverageconsumer.domain.MovingAverageResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MovingAverageCalculator {

    private final int windowSize;
    private final Map<String, Deque<BigDecimal>> windowsBySymbol = new ConcurrentHashMap<>();

    public MovingAverageCalculator(@Value("${app.moving-average.window-size:10}") int windowSize) {
        if (windowSize <= 0) {
            throw new IllegalArgumentException("windowSize must be positive, got: " + windowSize);
        }
        this.windowSize = windowSize;
    }

    public MovingAverageResult addPrice(String symbol, BigDecimal price) {
        if (symbol == null || price == null) {
            throw new IllegalArgumentException("symbol and price must not be null");
        }

        Deque<BigDecimal> window = windowsBySymbol.computeIfAbsent(
                symbol,
                k -> new ArrayDeque<>(windowSize + 1)
        );

        synchronized (window) {
            window.addLast(price);
            if (window.size() > windowSize) {
                window.removeFirst();
            }

            BigDecimal sum = window.stream()
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal average = sum.divide(
                    BigDecimal.valueOf(window.size()),
                    8,
                    RoundingMode.HALF_UP
            );

            return new MovingAverageResult(symbol, average, window.size(), windowSize);
        }
    }

    public int trackedSymbolsCount() {
        return windowsBySymbol.size();
    }
}