package com.programmingwithjonny.restaurants.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class OrderDetailsResponse {

    @Id
    private Long orderId;
    private String restaurantName;
    private Long restaurantId;
    private Date orderDate;
    private String customerName;
    private String customerEmailAddress;
    @ElementCollection
    private List<MenuItemDetails> menuItems;

    public OrderDetailsResponse(Long orderId, String restaurantName, Long restaurantId, Date orderDate, String customerName, String customerEmailAddress, List<MenuItemDetails> menuItems) {
        this.orderId = orderId;
        this.restaurantName = restaurantName;
        this.orderDate = orderDate;
        this.customerName = customerName;
        this.customerEmailAddress = customerEmailAddress;
        this.menuItems = menuItems;
        this.restaurantId = restaurantId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public OrderDetailsResponse() {

    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmailAddress() {
        return customerEmailAddress;
    }

    public void setCustomerEmailAddress(String customerEmailAddress) {
        this.customerEmailAddress = customerEmailAddress;
    }

    public List<MenuItemDetails> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(List<MenuItemDetails> menuItems) {
        this.menuItems = menuItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailsResponse that = (OrderDetailsResponse) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(restaurantName, that.restaurantName) && Objects.equals(orderDate, that.orderDate) && Objects.equals(customerName, that.customerName) && Objects.equals(customerEmailAddress, that.customerEmailAddress) && Objects.equals(menuItems, that.menuItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, restaurantName, orderDate, customerName, customerEmailAddress, menuItems);
    }
}
