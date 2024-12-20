package com.example.discount.api;

import com.example.shared.discount.commands.CreateDiscountCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/discounts")
public class DiscountController {

    private final CommandGateway commandGateway;

    public DiscountController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createDiscount(@RequestBody CreateDiscountRequest request) {
        String discountCode = request.discountCode() != null ? request.discountCode() : UUID.randomUUID().toString();
        commandGateway.send(new CreateDiscountCommand(discountCode, request.initialCount()));
        return "Discount created with code: " + discountCode;
    }

    record CreateDiscountRequest(String discountCode, int initialCount) {}
}