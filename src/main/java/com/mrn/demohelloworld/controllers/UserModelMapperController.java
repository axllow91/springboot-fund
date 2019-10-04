package com.mrn.demohelloworld.controllers;

import com.mrn.demohelloworld.dtos.UserMnDto;
import com.mrn.demohelloworld.entities.User;
import com.mrn.demohelloworld.exceptions.UserNotFoundException;
import com.mrn.demohelloworld.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
@RequestMapping("/modelmapper/users")
@Validated
public class UserModelMapperController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserModelMapperController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public UserMnDto getUserById(@PathVariable("id") @Min(1) Long id) throws UserNotFoundException {
        Optional<User> userOptional = userService.getUserById(id);

        if(!userOptional.isPresent()) {
             throw new UserNotFoundException("User not found!");
        }

        User user = userOptional.get();
        UserMnDto userMnDto = modelMapper.map(user, UserMnDto.class);
        return userMnDto;
    }
}
