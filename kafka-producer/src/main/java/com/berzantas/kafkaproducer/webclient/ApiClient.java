package com.berzantas.kafkaproducer.webclient;

import com.berzantas.kafkaproducer.model.PriceEvent;

import java.util.List;

public interface ApiClient {

    public List<PriceEvent> getPriceEvents();
}
