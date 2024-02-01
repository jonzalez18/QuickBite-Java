package com.programmingwithjonny.restaurants.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "customer")
public class Customer {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    @Column(name = "customer_name")
        private String customer_name;
    @Column(name = "customer_email_address")
        private String customer_email_address;

    public Customer() {
    }

    public Customer(Long id, String customer_name, String customer_email_address) {
        this.id = id;
        this.customer_name = customer_name;
        this.customer_email_address = customer_email_address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_email_address() {
        return customer_email_address;
    }

    public void setCustomer_email_address(String customer_email_address) {
        this.customer_email_address = customer_email_address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) && Objects.equals(customer_name, customer.customer_name) && Objects.equals(customer_email_address, customer.customer_email_address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customer_name, customer_email_address);
    }
}
