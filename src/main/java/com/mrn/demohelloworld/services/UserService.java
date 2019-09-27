package com.mrn.demohelloworld.services;

import com.mrn.demohelloworld.entities.User;
import com.mrn.demohelloworld.exceptions.UserExistsException;
import com.mrn.demohelloworld.exceptions.UserNotFoundException;
import com.mrn.demohelloworld.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // create user
    public User creatUser(User user) throws UserExistsException {
        // if user exists using the username throw exception
        User existingUser = userRepository.getUserByUsername(user.getUsername());
        if(existingUser != null)
            throw new UserExistsException("User already exists in Repository");

        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent())
            throw new UserNotFoundException("User not found in user Repository");
        return user;
    }

    // update
    public User updateUserById(Long id, User user) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);

        if(!optionalUser.isPresent())
            throw new UserNotFoundException("User not found in user Repository. Please provide the correct user id");

        user.setId(id);
        return userRepository.save(user);
    }

    // delete by id
    public void deleteUserById(Long id) {
        if(!userRepository.findById(id).isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User not found in Repository. Please provide the correct user id");
    }



    // get all Users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // get user by username
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }

}
