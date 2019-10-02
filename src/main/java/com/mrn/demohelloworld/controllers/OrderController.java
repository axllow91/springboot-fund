package com.mrn.demohelloworld.controllers;

import com.mrn.demohelloworld.entities.Order;
import com.mrn.demohelloworld.entities.User;
import com.mrn.demohelloworld.exceptions.UserNotFoundException;
import com.mrn.demohelloworld.repositories.OrderRepository;
import com.mrn.demohelloworld.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class OrderController {
    private UserRepository userRepository;
    private OrderRepository orderRepository;

    public OrderController(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    // get all Orders for a user
    @GetMapping("/{userId}/orders")
    public List<Order> getAllOrders(@PathVariable("userId") Long userId) throws UserNotFoundException{
        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent())
            throw new UserNotFoundException("User not found!");

        // returns all orders
        return userOptional.get().getOrders();
    }

    // Create order
    @PostMapping("/{userId}/orders")
    public Order createOrder(@PathVariable("userId") Long userId, @RequestBody Order order) throws UserNotFoundException {
        Optional<User> userOptional = userRepository.findById(userId);

        if(!userOptional.isPresent())
            throw new UserNotFoundException("User not found!");

        // get the user from repository
        User user = userOptional.get();

        // set the order to the user
        order.setUser(user);

        // save the new order created
        return orderRepository.save(order);

    }

    // Get order by order id
    @GetMapping("/{userId}/orders/{orderId}")
    public Optional<Order> getOrderByOrderId(@PathVariable("userId") Long userId,
                                             @PathVariable("orderId") Long orderId) throws UserNotFoundException {

        Optional<User> userOptional = userRepository.findById(userId);

        // check if user exists
        if(!userOptional.isPresent())
            throw new UserNotFoundException("User not found!");

        // if user exists return order by id
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if(!orderOptional.isPresent()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found!");

        User user = userOptional.get();
        Order order = orderOptional.get();

        if(!user.getId().equals(order.getUser().getId()))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "OrderId '" + orderId + "' does not exist!");

        return orderOptional;
    }


}
