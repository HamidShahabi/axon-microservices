package com.example.order.saga;

import com.example.shared.order.events.*;
import com.example.shared.discount.events.*;
import com.example.shared.payment.events.*;
import com.example.shared.discount.commands.*;
import com.example.shared.payment.commands.*;
import com.example.shared.order.commands.*;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.*;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import static org.axonframework.modelling.saga.SagaLifecycle.associationValues;

@Saga
public class OrderSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    private double orderAmount;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderCreatedEvent event) {
        // Start the saga and associate with orderId
        SagaLifecycle.associateWith("orderId", event.orderId());
        this.orderAmount = event.amount();

        // If there's a discount code, associate and request discount reservation
        if (event.discountCode() != null && !event.discountCode().isEmpty()) {
            SagaLifecycle.associateWith("discountCode", event.discountCode());
            commandGateway.send(new ReserveDiscountCommand(event.discountCode()));
        } else {
            // No discount code, proceed to payment request
            requestPayment(event.orderId(), event.amount());
        }
    }

    @SagaEventHandler(associationProperty = "discountCode")
    public void on(DiscountReservedEvent event) {
        String orderId = associationValues().stream()
                .filter(assoc -> "orderId".equals(assoc.getKey()))
                .map(AssociationValue::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Association value for 'orderId' not found"));

        // Now that discount is reserved, request payment
        requestPayment(orderId, orderAmount);
    }

    @SagaEventHandler(associationProperty = "discountCode")
    public void on(DiscountReservationFailedEvent event) {
        String orderId = associationValues().stream()
                .filter(assoc -> "orderId".equals(assoc.getKey()))
                .map(AssociationValue::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Association value for 'orderId' not found"));

        // Discount not available, cancel order
        commandGateway.send(new CancelOrderCommand(orderId));
        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(PaymentApprovedEvent event) {
        commandGateway.send(new ConfirmOrderCommand(event.orderId()));
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(PaymentDeclinedEvent event) {
        // If payment is declined, release discount if any, otherwise cancel order
        String discountCode = associationValues().stream()
                .filter(assoc -> "discountCode".equals(assoc.getKey()))
                .map(AssociationValue::getValue)
                .findFirst()
                .orElse(null);

        if (discountCode != null) {
            commandGateway.send(new ReleaseDiscountCommand(discountCode));
        } else {
            commandGateway.send(new CancelOrderCommand(event.orderId()));
        }
    }

    @SagaEventHandler(associationProperty = "discountCode")
    public void on(DiscountReleasedEvent event) {
        String orderId = associationValues().stream()
                .filter(assoc -> "orderId".equals(assoc.getKey()))
                .map(AssociationValue::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Association value for 'orderId' not found"));

        commandGateway.send(new CancelOrderCommand(orderId));
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderConfirmedEvent event) {
        SagaLifecycle.end();
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void on(OrderCancelledEvent event) {
        SagaLifecycle.end();
    }

    private void requestPayment(String orderId, double amount) {
        commandGateway.send(new RequestPaymentCommand(orderId, amount));
    }
}