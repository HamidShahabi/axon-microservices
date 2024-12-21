package com.example.discount.api;

import com.example.discount.query.DiscountEntity;
import com.example.discount.query.DiscountRepository;
import com.example.shared.discount.commands.CreateDiscountCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/discounts")
public class DiscountController {

    private final CommandGateway commandGateway;

    private final DiscountRepository repository;

    public DiscountController(CommandGateway commandGateway, DiscountRepository repository) {
        this.commandGateway = commandGateway;
        this.repository = repository;
    }

    @PostMapping
    public String createDiscount(@RequestBody CreateDiscountRequest request) {
        String discountCode = request.discountCode() != null ? request.discountCode() : UUID.randomUUID().toString();
        commandGateway.send(new CreateDiscountCommand(discountCode, request.initialCount(), request.percentage()));
        return "Discount created with code: " + discountCode;
    }
    @GetMapping
    public List<DiscountEntity> getAllDiscounts() {
        return repository.findAll();
    }

    @GetMapping("/{code}")
    public DiscountEntity getDiscountByCode(@PathVariable String code) {
        return repository.findById(code).orElseThrow(() -> new RuntimeException("Discount not found"));
    }
    record CreateDiscountRequest(String discountCode, int initialCount, double percentage) {}
}