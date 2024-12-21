package com.example.payment.api;

import com.example.payment.query.PaymentEntity;
import com.example.payment.query.PaymentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentRepository repository;

    public PaymentController(PaymentRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<PaymentEntity> getAllPayments() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public PaymentEntity getPaymentById(@PathVariable String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
    }
}