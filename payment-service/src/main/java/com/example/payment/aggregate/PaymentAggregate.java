package com.example.payment.aggregate;

import com.example.shared.payment.commands.RequestPaymentCommand;
import com.example.shared.payment.events.PaymentApprovedEvent;
import com.example.shared.payment.events.PaymentDeclinedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.Random;
import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
public class PaymentAggregate {

    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private double amount;
    private String status;

    public PaymentAggregate() {}

    @CommandHandler
    public PaymentAggregate(RequestPaymentCommand command) {
        this.paymentId = UUID.randomUUID().toString(); // Generate a unique Payment ID
        this.orderId = command.orderId();
        this.amount = command.amount();
        this.status = "PENDING";

        // Simulate payment logic: Randomly approve or decline
        boolean success = new Random().nextBoolean();
        if (success) {
            apply(new PaymentApprovedEvent(command.orderId(), command.amount()));
        } else {
            apply(new PaymentDeclinedEvent(command.orderId(), "Card declined"));
        }
    }

    @EventSourcingHandler
    public void on(PaymentApprovedEvent event) {
        this.status = "APPROVED";
    }

    @EventSourcingHandler
    public void on(PaymentDeclinedEvent event) {
        this.status = "DECLINED";
    }
}
