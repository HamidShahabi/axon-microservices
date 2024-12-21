package com.example.payment.query;

import com.example.shared.payment.events.PaymentApprovedEvent;
import com.example.shared.payment.events.PaymentDeclinedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventHandler {

    private final PaymentRepository repository;

    public PaymentEventHandler(PaymentRepository repository) {
        this.repository = repository;
    }

    @EventHandler
    public void on(PaymentApprovedEvent event) {
        PaymentEntity payment = new PaymentEntity();
        payment.setPaymentId(java.util.UUID.randomUUID().toString()); // Unique identifier
        payment.setOrderId(event.orderId());
        payment.setAmount(event.amount());
        payment.setStatus("APPROVED");
        repository.save(payment);
    }

    @EventHandler
    public void on(PaymentDeclinedEvent event) {
        PaymentEntity payment = new PaymentEntity();
        payment.setPaymentId(java.util.UUID.randomUUID().toString()); // Unique identifier
        payment.setOrderId(event.orderId());
        payment.setStatus("DECLINED");
        repository.save(payment);
    }
}