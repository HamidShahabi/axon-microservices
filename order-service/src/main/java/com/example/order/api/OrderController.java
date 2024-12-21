package com.example.order.api;

import com.example.order.aggregate.model.OrderQueryModel;
import com.example.order.query.OrderEntity;
import com.example.order.query.OrderRepository;
import com.example.shared.order.commands.CreateOrderCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CommandGateway commandGateway;
    private final OrderRepository repository;

    public OrderController(CommandGateway commandGateway, OrderRepository repository) {
        this.commandGateway = commandGateway;
        this.repository = repository;
    }

    @PostMapping
    public String createOrder(@RequestBody CreateOrderRequest request) {
        String orderId = request.orderId() != null ? request.orderId() : UUID.randomUUID().toString();
        commandGateway.send(new CreateOrderCommand(orderId, request.discountCode(), request.amount()));
        return "Order created with ID: " + orderId;
    }

    @GetMapping
    public List<OrderEntity> getAllOrders() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public OrderEntity getOrderById(@PathVariable String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }
    record CreateOrderRequest(String orderId, String discountCode, double amount) {}
}