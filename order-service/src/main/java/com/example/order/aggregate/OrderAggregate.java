package com.example.order.aggregate;

import com.example.shared.order.events.*;
import com.example.shared.order.commands.*;
import com.example.shared.payment.events.*;
import com.example.shared.payment.commands.*;



import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderId;
    private String discountCode;
    private double amount;
    private String status;

    protected OrderAggregate(){}

    @CommandHandler
    public OrderAggregate(CreateOrderCommand cmd) {
        apply(new OrderCreatedEvent(cmd.orderId(), cmd.discountCode(), cmd.amount()));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.orderId();
        this.discountCode = event.discountCode();
        this.amount = event.amount();
        this.status = "PENDING";
    }

    @CommandHandler
    public void handle(ConfirmOrderCommand cmd) {
        apply(new OrderConfirmedEvent(orderId));
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        this.status = "CONFIRMED";
    }

    @CommandHandler
    public void handle(CancelOrderCommand cmd) {
        apply(new OrderCancelledEvent(orderId));
    }

    @EventSourcingHandler
    public void on(OrderCancelledEvent event) {
        this.status = "CANCELLED";
    }
}
