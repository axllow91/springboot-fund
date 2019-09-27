package com.mrn.demohelloworld.services;

import com.mrn.demohelloworld.entities.User;
import com.mrn.demohelloworld.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // create user
    public User creatUser(User user) {
        return userRepository.save(user);

    }

    public Optional<User> getUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    // update
    public User updateUserById(Long id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }

    // delete by id
    public void deleteUserById(Long id) {
        if(userRepository.findById(id).isPresent())
            userRepository.deleteById(id);
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
