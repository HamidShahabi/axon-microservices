package com.example.discount.query;

import com.example.shared.discount.events.*;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class DiscountEventHandler {

    private final DiscountRepository repository;

    public DiscountEventHandler(DiscountRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(DiscountCreatedEvent event) {
        DiscountEntity discount = new DiscountEntity();
        discount.setDiscountCode(event.discountCode());
        discount.setAvailableCount(event.initialCount());
        discount.setPercentage(event.percentage());
        repository.save(discount);
    }

    @EventHandler
    public void on(DiscountReservedEvent event) {
        DiscountEntity discount = repository.findById(event.discountCode()).orElseThrow();
        discount.setAvailableCount(discount.getAvailableCount() - 1);
        repository.save(discount);
    }

    @EventHandler
    public void on(DiscountReleasedEvent event) {
        DiscountEntity discount = repository.findById(event.discountCode()).orElseThrow();
        discount.setAvailableCount(discount.getAvailableCount() + 1);
        repository.save(discount);
    }
}