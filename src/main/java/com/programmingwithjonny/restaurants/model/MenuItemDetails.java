package com.programmingwithjonny.restaurants.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;

import java.math.BigDecimal;
import java.util.Objects;
@Embeddable
public class MenuItemDetails  {
    private Long Id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;

    public MenuItemDetails() {
    }

    public MenuItemDetails(Long id, String name, String description, BigDecimal price, int quantity) {
        Id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
