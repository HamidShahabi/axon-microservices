package com.example.shared.payment.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record RequestPaymentCommand(@TargetAggregateIdentifier String orderId, double amount) {}
