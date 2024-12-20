package com.example.order.api;

import com.example.order.aggregate.model.OrderQueryModel;
import com.example.shared.order.commands.CreateOrderCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CommandGateway commandGateway;
    private final OrderQueryModel orderQueryModel;


    public OrderController(CommandGateway commandGateway, OrderQueryModel orderQueryModel) {
        this.commandGateway = commandGateway;
        this.orderQueryModel = orderQueryModel;
    }

    @PostMapping
    public String createOrder(@RequestBody CreateOrderRequest request) {
        String orderId = request.orderId() != null ? request.orderId() : UUID.randomUUID().toString();
        commandGateway.send(new CreateOrderCommand(orderId, request.discountCode(), request.amount()));
        return "Order created with ID: " + orderId;
    }

    @GetMapping
    public Map<String, String> getAllOrders() {
        return orderQueryModel.getOrders();
    }
    record CreateOrderRequest(String orderId, String discountCode, double amount) {}
}