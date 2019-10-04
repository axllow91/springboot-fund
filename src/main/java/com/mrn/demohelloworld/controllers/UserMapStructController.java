package com.mrn.demohelloworld.controllers;

import com.mrn.demohelloworld.dtos.UserMsDto;
import com.mrn.demohelloworld.entities.User;
import com.mrn.demohelloworld.exceptions.UserNotFoundException;
import com.mrn.demohelloworld.mapper.UserMapper;
import com.mrn.demohelloworld.repositories.UserRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mapstruct/users")
@Validated
public class UserMapStructController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserMapStructController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserMsDto> getAllUserDtos() {
        return userMapper.usersToUserDtos(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public UserMsDto getUserById(@PathVariable("id") Long id) throws UserNotFoundException{
        Optional<User> userOptional = userRepository.findById(id);
        if(!userOptional.isPresent())
            throw new UserNotFoundException("User not found!");

        User user = userOptional.get();

        return userMapper.userToUserMsDto(user);

    }

}
