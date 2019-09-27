package com.mrn.demohelloworld.controllers;

import com.mrn.demohelloworld.entities.User;
import com.mrn.demohelloworld.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // create user
    // @RequestBody
    // @PostMapping
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userService.creatUser(user);
    }

    // get user by id
    // @PathVariable
    // @GetMapping
    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // get user by username
    @GetMapping("/users/byusername/{username}")
    public User getUserByUsername(@PathVariable("username") String username) {
        return userService.getUserByUsername(username);
    }



    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // update user by id
    @PutMapping("/users/{id}")
    public User updateUserById(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUserById(id, user);
    }

    // delete user by id
    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }
}
