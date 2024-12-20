package com.example.shared.payment.events;

public record PaymentDeclinedEvent(String orderId, String reason) {
}
