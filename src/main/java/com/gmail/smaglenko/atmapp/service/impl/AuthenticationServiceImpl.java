package com.gmail.smaglenko.atmapp.service.impl;

import com.gmail.smaglenko.atmapp.exception.AuthenticationException;
import com.gmail.smaglenko.atmapp.model.Role;
import com.gmail.smaglenko.atmapp.model.Role.RoleName;
import com.gmail.smaglenko.atmapp.model.User;
import com.gmail.smaglenko.atmapp.service.AuthenticationService;
import com.gmail.smaglenko.atmapp.service.RoleService;
import com.gmail.smaglenko.atmapp.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User register(String username, String password) {
        Optional<User> userFromDb = userService.findByUsername(username);
        if (userFromDb.isPresent()) {
            throw new AuthenticationException("The user with this name already exists");
        }
        Role role = roleService.findByRoleName(RoleName.USER);
        if (role == null) {
            role = new Role();
            role.setRoleName(RoleName.USER);
            role = roleService.save(role);
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.getRoles().add(role);
        return userService.save(user);
    }

    @Override
    public User login(String username, String password) {
        Optional<User> userFromDb = userService.findByUsername(username);
        if (userFromDb.isEmpty()
                || userFromDb.get().getPassword().equals(passwordEncoder.encode(password))) {
            throw new AuthenticationException("Incorrect username or password!!!");
        }
        return userFromDb.get();
    }
}
