package com.example.shared.payment.events;

public record PaymentApprovedEvent(String orderId, double amount) {}
