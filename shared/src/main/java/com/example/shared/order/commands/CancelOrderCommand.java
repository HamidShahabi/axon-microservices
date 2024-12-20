package com.example.shared.order.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CancelOrderCommand(@TargetAggregateIdentifier String orderId) {
}

