package com.berzantas.movingaverageconsumer.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "moving_averages", indexes = {
        @Index(name = "idx_moving_averages_symbol_time",
            columnList = "symbol, calculated_at DESC")
})
public class MovingAverage {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "symbol", nullable = false, length = 20)
    private String symbol;

    @Column(name = "average_price", nullable = false, precision = 20, scale = 8)
    private BigDecimal averagePrice;

    @Column(name = "window_size", nullable = false)
    private Integer windowSize;

    @Column(name = "sample_count", nullable = false)
    private Integer sampleCount;

    @Column(name = "calculated_at", nullable = false)
    private OffsetDateTime calculatedAt;

    public MovingAverage(String symbol, BigDecimal averagePrice,
                         Integer windowSize, Integer sampleCount,
                         OffsetDateTime calculatedAt) {
        this.symbol = symbol;
        this.averagePrice = averagePrice;
        this.windowSize = windowSize;
        this.sampleCount = sampleCount;
        this.calculatedAt = calculatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovingAverage that)) return false;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
