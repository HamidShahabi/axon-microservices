package com.example.restaurant.query;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity, String> {
    
    List<RestaurantEntity> findByActiveTrue();
    
    @Query("SELECT r FROM RestaurantEntity r WHERE r.active = true " +
           "AND (6371 * acos(cos(radians(:latitude)) * cos(radians(r.latitude)) * " +
           "cos(radians(r.longitude) - radians(:longitude)) + " +
           "sin(radians(:latitude)) * sin(radians(r.latitude)))) <= :radiusKm " +
           "ORDER BY (6371 * acos(cos(radians(:latitude)) * cos(radians(r.latitude)) * " +
           "cos(radians(r.longitude) - radians(:longitude)) + " +
           "sin(radians(:latitude)) * sin(radians(r.latitude))))")
    List<RestaurantEntity> findNearbyRestaurants(@Param("latitude") double latitude, 
                                                 @Param("longitude") double longitude, 
                                                 @Param("radiusKm") double radiusKm);
}