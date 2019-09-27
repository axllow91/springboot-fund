package com.mrn.demohelloworld.controllers;

import com.mrn.demohelloworld.entities.User;
import com.mrn.demohelloworld.exceptions.UserExistsException;
import com.mrn.demohelloworld.exceptions.UserNotFoundException;
import com.mrn.demohelloworld.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
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
    public ResponseEntity<User> createUser(@RequestBody User user, UriComponentsBuilder builder) {
        try {
            userService.creatUser(user);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
            return new ResponseEntity<User>(headers, HttpStatus.CREATED);
        }catch (UserExistsException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    // get user by id
    // @PathVariable
    // @GetMapping
    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {
        try {
            return userService.getUserById(id);
        } catch (UserNotFoundException ex) {
            // return the http status and the message of the usernotfoundexc
            // (404 + message added in the service layer see the method)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
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
        try {
            return userService.updateUserById(id, user);
        }catch (UserNotFoundException ex) {
            // 400
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

    // delete user by id
    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }
}