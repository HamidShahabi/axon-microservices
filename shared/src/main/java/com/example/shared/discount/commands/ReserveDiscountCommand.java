package com.example.shared.discount.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public record ReserveDiscountCommand(@TargetAggregateIdentifier String discountCode) {
}
