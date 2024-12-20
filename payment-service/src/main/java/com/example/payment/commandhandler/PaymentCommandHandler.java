package com.example.payment.commandhandler;

import com.example.shared.payment.commands.RequestPaymentCommand;
import com.example.shared.payment.events.PaymentApprovedEvent;
import com.example.shared.payment.events.PaymentDeclinedEvent;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class PaymentCommandHandler {

    private final EventGateway eventGateway;

    public PaymentCommandHandler(EventGateway eventGateway) {
        this.eventGateway = eventGateway;
    }
    // Simulate payment logic
    @CommandHandler
    public void handle(RequestPaymentCommand cmd) {
        // Randomly approve or decline
        boolean success = new Random().nextBoolean();
        if (success) {
            eventGateway.publish(new PaymentApprovedEvent(cmd.orderId()));
        } else {
            eventGateway.publish(new PaymentDeclinedEvent(cmd.orderId(), "Card declined"));
        }
    }
}
