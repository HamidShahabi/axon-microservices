package com.example.restaurant.api;

import com.example.restaurant.query.RestaurantEntity;
import com.example.restaurant.query.RestaurantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurants")
public class RestaurantController {

    private final RestaurantRepository repository;

    public RestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/near")
    public ResponseEntity<List<RestaurantEntity>> getNearRestaurants(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5.0") double radiusKm) {
        
        List<RestaurantEntity> nearbyRestaurants = repository.findNearbyRestaurants(
            latitude, longitude, radiusKm);
        
        return ResponseEntity.ok(nearbyRestaurants);
    }

    @GetMapping
    public List<RestaurantEntity> getAllRestaurants() {
        return repository.findByActiveTrue();
    }

    @GetMapping("/{id}")
    public RestaurantEntity getRestaurantById(@PathVariable String id) {
        return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Restaurant not found"));
    }

    @PostMapping
    public ResponseEntity<String> createRestaurant(@RequestBody CreateRestaurantRequest request) {
        RestaurantEntity restaurant = new RestaurantEntity(
            request.restaurantId(),
            request.name(),
            request.address(),
            request.latitude(),
            request.longitude(),
            request.cuisine(),
            true
        );
        
        repository.save(restaurant);
        return ResponseEntity.ok("Restaurant created with ID: " + request.restaurantId());
    }

    public record CreateRestaurantRequest(
        String restaurantId,
        String name,
        String address,
        double latitude,
        double longitude,
        String cuisine
    ) {}
}