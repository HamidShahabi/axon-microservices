package com.example.shared.order.events;

public record OrderCreatedEvent(String orderId, String discountCode, double amount) {}

