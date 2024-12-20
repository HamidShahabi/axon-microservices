package com.example.shared.order.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;
public record CreateOrderCommand(@TargetAggregateIdentifier String orderId, String discountCode, double amount) {}

