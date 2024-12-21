package com.example.order.query;

import com.example.shared.order.events.*;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {

    private final OrderRepository repository;

    public OrderEventHandler(OrderRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        OrderEntity order = new OrderEntity();
        order.setOrderId(event.orderId());
        order.setDiscountCode(event.discountCode());
        order.setAmount(event.amount());
        order.setStatus("PENDING");
        repository.save(order);
    }

    @EventHandler
    public void on(OrderConfirmedEvent event) {
        OrderEntity order = repository.findById(event.orderId()).orElseThrow();
        order.setStatus("CONFIRMED");
        repository.save(order);
    }

    @EventHandler
    public void on(OrderCancelledEvent event) {
        OrderEntity order = repository.findById(event.orderId()).orElseThrow();
        order.setStatus("CANCELLED");
        repository.save(order);
    }
}