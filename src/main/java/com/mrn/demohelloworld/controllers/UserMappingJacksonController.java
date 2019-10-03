package com.mrn.demohelloworld.controllers;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.mrn.demohelloworld.entities.User;
import com.mrn.demohelloworld.exceptions.UserNotFoundException;
import com.mrn.demohelloworld.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


// Demonstrate mapping jackson
@RestController
@RequestMapping(value = "jacksonfilter/users")
@Validated
public class UserMappingJacksonController {

    private final UserService userService;

    public UserMappingJacksonController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}") // fields with hashset
    public MappingJacksonValue getUserById(@PathVariable("id") @Min(1) Long id) {
        try {
            Optional<User> userOptional = userService.getUserById(id);
            User user = userOptional.get();

            Set<String> fields = new HashSet<>();
            fields.add("userId");
            fields.add("username");
            fields.add("ssn");
            fields.add("orders");

            FilterProvider filterProvider = new SimpleFilterProvider()
                    .addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields));
            MappingJacksonValue mapper = new MappingJacksonValue(user);

            mapper.setFilters(filterProvider);

            return mapper;

        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @GetMapping("/params/{id}") // fields with @RequestParam
    public MappingJacksonValue getUserById2(@PathVariable("id") @Min(1) Long id, @RequestParam Set<String> fields) {
        try {
            Optional<User> userOptional = userService.getUserById(id);
            User user = userOptional.get();

            FilterProvider filterProvider = new SimpleFilterProvider()
                    .addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept(fields));
            MappingJacksonValue mapper = new MappingJacksonValue(user);

            mapper.setFilters(filterProvider);

            return mapper;

        } catch (UserNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }
}
