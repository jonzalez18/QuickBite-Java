package com.programmingwithjonny.restaurants;

import com.programmingwithjonny.restaurants.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
