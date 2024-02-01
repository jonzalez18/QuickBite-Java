package com.programmingwithjonny.restaurants;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.programmingwithjonny.restaurants.model.MenuItemDetails;
import com.programmingwithjonny.restaurants.model.OrderDetailsResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import org.slf4j.ILoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/order")
public class OrderController{

    private final EntityManager entityManager;

    @Autowired
    public OrderController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createOrder(@RequestBody Map<String, Object> orderRequest) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Long customerId = Long.parseLong(orderRequest.get("p_customer_id").toString());
            Long restaurantId = Long.parseLong(orderRequest.get("p_restaurant_id").toString());
            Date orderDate = Date.valueOf(orderRequest.get("p_order_date").toString());
            String itemsJson = objectMapper.writeValueAsString(orderRequest.get("p_items_json"));

            StoredProcedureQuery storedProcedureQuery = entityManager
                    .createStoredProcedureQuery("InsertOrder")
                    .registerStoredProcedureParameter("p_customer_id", Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_restaurant_id", Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_order_date", Date.class, ParameterMode.IN)
                    .registerStoredProcedureParameter("p_items_json", String.class, ParameterMode.IN)
                    .setParameter("p_customer_id", customerId)
                    .setParameter("p_restaurant_id", restaurantId)
                    .setParameter("p_order_date", orderDate)
                    .setParameter("p_items_json", itemsJson);

            storedProcedureQuery.execute();

            return ResponseEntity.ok("Order created successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create order: " + e.getMessage());
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsResponse> getOrderDetails(@PathVariable Long orderId) {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("GetOrderDetails");

        storedProcedureQuery.registerStoredProcedureParameter("p_order_id", Long.class, ParameterMode.IN);
        storedProcedureQuery.setParameter("p_order_id", orderId);

        List<Object[]> result = storedProcedureQuery.getResultList();

        if (result.isEmpty()) {
            // Handle case when no results are returned
            return ResponseEntity.notFound().build();
        }

        // Assuming you have a class OrderDetailsResponse to represent the response
        OrderDetailsResponse orderDetailsResponse = mapToOrderDetailsResponse(result.get(0));
        return ResponseEntity.ok(orderDetailsResponse);
    }
    ObjectMapper objectMapper = new ObjectMapper();
    // You can create a method to map the result to your response DTO
    private OrderDetailsResponse mapToOrderDetailsResponse(Object[] result) {
         OrderDetailsResponse response = new OrderDetailsResponse();

         response.setOrderId((Long) result[0]);
         response.setRestaurantName((String) result[1]);
         response.setRestaurantId((Long) result[2]);
         response.setOrderDate((Date) result[3]);
         response.setCustomerName((String) result[4]);
         response.setCustomerEmailAddress((String) result[5]);

         System.out.println( "Result[5] content: {}" + result[6]);
        if (result[6] instanceof List<?>) {
            // If result[5] is already a List, set it directly
            response.setMenuItems((List<MenuItemDetails>) result[6]);
        } else if (result[6] instanceof String) {
            // If result[5] is a String, try to parse it as a JSON array
            String jsonString = (String) result[6];
            if (!jsonString.equals("{}")) {
                try {
                    // Parse the JSON array and set it to response
                    ObjectMapper objectMapper = new ObjectMapper();
                    List<MenuItemDetails> menuItems = objectMapper.readValue(jsonString, new TypeReference<List<MenuItemDetails>>() {});
                    response.setMenuItems(menuItems);
                } catch (JsonProcessingException e) {
                    System.out.println("Error parsing JSON array for menuItems" + e);
                    response.setMenuItems(null); // Handle parsing error
                }
            } else {
                response.setMenuItems(null); // Handle empty JSON object
            }
        } else {
            response.setMenuItems(null); // Handle unexpected type
        }

        // Return the mapped response
        return response;
    }
}
