package com.mrn.demohelloworld.controllers;

import com.mrn.demohelloworld.dtos.UserDtoV1;
import com.mrn.demohelloworld.dtos.UserDtoV2;
import com.mrn.demohelloworld.entities.User;
import com.mrn.demohelloworld.exceptions.UserNotFoundException;
import com.mrn.demohelloworld.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
@RequestMapping("/versioning/headers/users")
public class UserCustomHeaderVersioningController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserCustomHeaderVersioningController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    // Custom header based versioning - v1
    @GetMapping(value = "/{id}", headers = "API-VERSION=1")
    public UserDtoV1 getUserById1(@PathVariable("id") @Min(1) Long id) throws UserNotFoundException {
        Optional<User> userOptional = userService.getUserById(id);

        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found!");
        }

        User user = userOptional.get();

        UserDtoV1 userDtoV1 = modelMapper.map(user, UserDtoV1.class);
        return userDtoV1;
    }

    // Custom header based versioning - v2
    @GetMapping(value = "/{id}", headers = "API-VERSION=2")
    public UserDtoV2 getUserById2(@PathVariable("id") @Min(1) Long id) throws UserNotFoundException {
        Optional<User> userOptional = userService.getUserById(id);

        if(!userOptional.isPresent()) {
            throw new UserNotFoundException("User not found!");
        }

        User user = userOptional.get();

        UserDtoV2 userDtoV2 = modelMapper.map(user, UserDtoV2.class);
        return userDtoV2;
    }
}
