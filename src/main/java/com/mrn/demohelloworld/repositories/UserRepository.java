package com.mrn.demohelloworld.repositories;

import com.mrn.demohelloworld.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User getUserByUsername(String username);
}
