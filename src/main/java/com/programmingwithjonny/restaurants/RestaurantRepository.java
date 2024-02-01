package com.programmingwithjonny.restaurants;

import com.programmingwithjonny.restaurants.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    Restaurant findOneByName(String name);

    List<Restaurant> findAllByNameContaining(String string);
}
