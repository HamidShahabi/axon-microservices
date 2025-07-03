package com.example.restaurant.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, String> {
    
    List<RestaurantEntity> findByActiveTrue();
    
    List<RestaurantEntity> findByActiveTrueAndLatitudeBetweenAndLongitudeBetween(
        double minLat, double maxLat, double minLng, double maxLng);
}