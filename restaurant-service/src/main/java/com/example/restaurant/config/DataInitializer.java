package com.example.restaurant.config;

import com.example.restaurant.query.RestaurantEntity;
import com.example.restaurant.query.RestaurantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RestaurantRepository repository;

    public DataInitializer(RestaurantRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            // Initialize some sample restaurants
            RestaurantEntity[] restaurants = {
                new RestaurantEntity(
                    UUID.randomUUID().toString(),
                    "Pizza Palace",
                    "123 Main Street, New York, NY",
                    40.7128, -74.0060, // NYC coordinates
                    "Italian",
                    true
                ),
                new RestaurantEntity(
                    UUID.randomUUID().toString(),
                    "Burger House",
                    "456 Broadway, New York, NY",
                    40.7580, -73.9855, // Times Square area
                    "American",
                    true
                ),
                new RestaurantEntity(
                    UUID.randomUUID().toString(),
                    "Sushi Zen",
                    "789 Fifth Avenue, New York, NY",
                    40.7505, -73.9934, // Midtown Manhattan
                    "Japanese",
                    true
                ),
                new RestaurantEntity(
                    UUID.randomUUID().toString(),
                    "Taco Fiesta",
                    "321 Houston Street, New York, NY",
                    40.7254, -74.0048, // SoHo area
                    "Mexican",
                    true
                ),
                new RestaurantEntity(
                    UUID.randomUUID().toString(),
                    "The French Bistro",
                    "654 Madison Avenue, New York, NY",
                    40.7614, -73.9776, // Upper East Side
                    "French",
                    true
                )
            };

            repository.saveAll(Arrays.asList(restaurants));
            System.out.println("Initialized " + restaurants.length + " sample restaurants");
        }
    }
}