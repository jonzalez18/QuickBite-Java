package com.programmingwithjonny.restaurants;

import com.programmingwithjonny.restaurants.RestaurantRepository;
import com.programmingwithjonny.restaurants.model.MenuItem;
import com.programmingwithjonny.restaurants.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/restaurant")
public class RestaurantController {

    private final RestaurantRepository restaurantRepository;
    @Autowired
    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> viewRestaurant(@PathVariable("id") Long id) {

        try {
            Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);

            if(optionalRestaurant.isPresent()) {
                Restaurant restaurant = optionalRestaurant.get();
                return ResponseEntity.ok(restaurant);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Iterable<Restaurant>> getAllRestaurants() {
        try {
            Iterable<Restaurant> optionalRestaurants = restaurantRepository.findAll();

            if(optionalRestaurants.iterator().hasNext()) {
                return ResponseEntity.ok(optionalRestaurants);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}

