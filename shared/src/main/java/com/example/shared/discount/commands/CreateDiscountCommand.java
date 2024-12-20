package com.example.shared.discount.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record CreateDiscountCommand(@TargetAggregateIdentifier String discountCode, int initialCount) {}

