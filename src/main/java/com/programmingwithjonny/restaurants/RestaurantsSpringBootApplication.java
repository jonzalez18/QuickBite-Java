package com.programmingwithjonny.restaurants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestaurantsSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantsSpringBootApplication.class, args);
    }

    @Bean
    public CorsConfig corsConfig() {
        return new CorsConfig();
    }
}
