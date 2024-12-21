package com.example.discount.aggregate;

import com.example.shared.discount.commands.*;
import com.example.shared.discount.events.*;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class DiscountAggregate {

    @AggregateIdentifier
    private String discountCode;
    private int availableCount;
    private double percentage;

    protected DiscountAggregate(){}

    @CommandHandler
    public DiscountAggregate(CreateDiscountCommand cmd) {
        apply(new DiscountCreatedEvent(cmd.discountCode(), cmd.initialCount(), cmd.percentage()));
    }

    @EventSourcingHandler
    public void on(DiscountCreatedEvent event) {
        this.discountCode = event.discountCode();
        this.availableCount = event.initialCount();
        this.percentage = event.percentage();
    }

    @CommandHandler
    public void handle(ReserveDiscountCommand cmd) {
        if (availableCount <= 0) {
            apply(new DiscountReservationFailedEvent(discountCode));
        } else {
            apply(new DiscountReservedEvent(discountCode, percentage));
        }
    }

    @EventSourcingHandler
    public void on(DiscountReservedEvent event) {
        this.availableCount -= 1;
    }

    @CommandHandler
    public void handle(ReleaseDiscountCommand cmd) {
        apply(new DiscountReleasedEvent(discountCode));
    }

    @EventSourcingHandler
    public void on(DiscountReleasedEvent event) {
        this.availableCount += 1;
    }
}
