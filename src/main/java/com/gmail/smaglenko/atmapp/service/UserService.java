package com.gmail.smaglenko.atmapp.service;

import com.gmail.smaglenko.atmapp.model.Role;
import com.gmail.smaglenko.atmapp.model.User;
import java.util.Optional;

public interface UserService {
    Optional<User> findByUsername(String username);

    User save(User user);

    User update(User user);

    User findById(Long userId);

    User addRoleToUser(Long userId, Role role);
}
