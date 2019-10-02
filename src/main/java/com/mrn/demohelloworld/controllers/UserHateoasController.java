package com.mrn.demohelloworld.controllers;

import com.mrn.demohelloworld.entities.Order;
import com.mrn.demohelloworld.entities.User;
import com.mrn.demohelloworld.exceptions.UserNotFoundException;
import com.mrn.demohelloworld.repositories.UserRepository;
import com.mrn.demohelloworld.services.UserService;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/hateoas/users")
@Validated
public class UserHateoasController {
    private final UserRepository userRepository;
    private final UserService userService;

    public UserHateoasController(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public Resource<User> getUserById(@PathVariable("id") @Min(1) Long id) {
        try {
            Optional<User> userOptional = userService.getUserById(id);

            User user = userOptional.get();
            Long userId = user.getUserId(); // we need userId for safe linking

            // create the safe link link/userId
            Link safeLink = ControllerLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
            user.add(safeLink);

            // adding to the resource wrapper domain object the user links
            Resource<User> finalResource = new Resource<>(user);
            return finalResource;

        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    // adding to every user the self link
    @GetMapping
    public Resources<User> getAllUsers() throws UserNotFoundException {
        List<User> usersList = userService.getAllUsers();

        for(User user: usersList) {
            // Self link
            Long userId = user.getUserId();
            Link selfLink = ControllerLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
            user.add(selfLink);

            // Relationship link with all orders
            // create a resource for all orders
            Resources<Order> ordersResources =
                    ControllerLinkBuilder.methodOn(OrderHateoasController.class).getAllOrders(userId);

            // Create and add a link to every user that have orders
            Link ordersLink = ControllerLinkBuilder.linkTo(ordersResources).withRel("all-orders");
            user.add(ordersLink);
        }

        // Self link for getAllUsers()
        // we dont need id because we are getting all the list
        Link selfLinkForAllUsers = ControllerLinkBuilder.linkTo(this.getClass()).withSelfRel();

        // create the resource for our users containing the user ref links and all users ref link
        Resources<User> userResource = new Resources<>(usersList, selfLinkForAllUsers);
        return userResource;

    }

}
