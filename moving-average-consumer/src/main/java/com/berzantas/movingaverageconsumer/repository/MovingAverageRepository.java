package com.berzantas.movingaverageconsumer.repository;

import com.berzantas.movingaverageconsumer.domain.MovingAverage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovingAverageRepository extends JpaRepository<MovingAverage, Long> {
    List<MovingAverage> findTop10BySymbolOrderByCalculatedAtDesc(String symbol);
}
