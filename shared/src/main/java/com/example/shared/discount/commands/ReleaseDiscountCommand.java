package com.example.shared.discount.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record ReleaseDiscountCommand(@TargetAggregateIdentifier String discountCode) {
}
