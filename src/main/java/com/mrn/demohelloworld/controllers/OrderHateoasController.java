package com.mrn.demohelloworld.controllers;

import com.mrn.demohelloworld.entities.Order;
import com.mrn.demohelloworld.entities.User;
import com.mrn.demohelloworld.exceptions.UserNotFoundException;
import com.mrn.demohelloworld.repositories.OrderRepository;
import com.mrn.demohelloworld.repositories.UserRepository;
import org.springframework.hateoas.Resources;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/hateoas/users")
@Validated
public class OrderHateoasController {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public OrderHateoasController(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping("/{userId}/orders")
    public Resources<Order> getAllOrders(@PathVariable("userId") Long userId) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent())
            throw new UserNotFoundException("User not found!");

        // returns all orders
        List<Order> ordersList = userOptional.get().getOrders();
        Resources<Order> resourcesOrder = new Resources<>(ordersList);
        return resourcesOrder;
    }


}
