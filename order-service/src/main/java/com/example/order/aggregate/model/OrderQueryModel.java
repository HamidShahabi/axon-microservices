package com.example.order.aggregate.model;

import com.example.shared.order.events.OrderCreatedEvent;
import com.example.shared.order.events.OrderConfirmedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OrderQueryModel {

    private final Map<String, String> orders = new ConcurrentHashMap<>();

    @EventHandler
    public void on(OrderCreatedEvent event) {
        orders.put(event.orderId(), "CREATED");
    }

    @EventHandler
    public void on(OrderConfirmedEvent event) {
        orders.put(event.orderId(), "CONFIRMED");
    }

    public Map<String, String> getOrders() {
        return orders;
    }
}