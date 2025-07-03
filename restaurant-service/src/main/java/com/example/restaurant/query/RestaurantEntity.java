package com.example.restaurant.query;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class RestaurantEntity {

    @Id
    private String restaurantId;
    private String name;
    private String address;
    private double latitude;
    private double longitude;
    private String cuisine;
    private boolean active;

    // Default constructor
    public RestaurantEntity() {}

    // Constructor
    public RestaurantEntity(String restaurantId, String name, String address, 
                          double latitude, double longitude, String cuisine, boolean active) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cuisine = cuisine;
        this.active = active;
    }

    // Getters and setters
    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}