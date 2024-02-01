package com.programmingwithjonny.restaurants;

import com.programmingwithjonny.restaurants.model.OrderDetailsResponse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<OrderDetailsResponse, Long> {
}
