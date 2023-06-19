package com.gmail.smaglenko.atmapp.repository;

import com.gmail.smaglenko.atmapp.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}