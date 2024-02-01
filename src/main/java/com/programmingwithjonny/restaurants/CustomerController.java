package com.programmingwithjonny.restaurants;

import com.programmingwithjonny.restaurants.model.Customer;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("id") Long id) {
        try {
            Optional<Customer> optionalCustomer = customerRepository.findById(id);

            if (optionalCustomer.isPresent()) {
                Customer customer = optionalCustomer.get();
                return ResponseEntity.ok(customer);
            } else {
                return ResponseEntity.status(404).body(null);
            }
        } catch (Exception e) {
            // Handle the exception and return an appropriate response
            return ResponseEntity.status(500).body(null);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<Iterable<Customer>> getAllCustomers(){

        try {
            return ResponseEntity.ok().body(customerRepository.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @PostMapping("/create")
    public ResponseEntity<String> createCustomer(@RequestBody @Validated Customer customer, BindingResult bindingResult) {
       if(bindingResult.hasErrors()) {
           return ResponseEntity
                   .badRequest()
                   .body("Validation errors:"
                           + bindingResult.getAllErrors());
       }

       try{
           customerRepository.save(customer);
           Long generatedId =  customer.getId();
           return ResponseEntity.ok("Customer created successfully, id: " + generatedId);
       } catch (Exception e) {
           return ResponseEntity
                   .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body("Failed to create customer");
       }
    }
}
