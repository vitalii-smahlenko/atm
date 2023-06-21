package com.gmail.smaglenko.atmapp.service.impl;

import com.gmail.smaglenko.atmapp.model.Role;
import com.gmail.smaglenko.atmapp.model.Role.RoleName;
import com.gmail.smaglenko.atmapp.model.User;
import com.gmail.smaglenko.atmapp.repository.UserRepository;
import com.gmail.smaglenko.atmapp.service.RoleService;
import com.gmail.smaglenko.atmapp.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    public User update(User user) {
        return repository.save(user);
    }

    @Override
    public User findById(Long userId) {
        return repository.findById(userId).orElseThrow(
                () -> new RuntimeException("Can't find user by ID " + userId)
        );
    }

    @Override
    @Transactional
    public User addRoleToUser(Long userId, RoleName roleName) {
        User userFromDb = repository.findById(userId).orElseThrow(
                () -> new RuntimeException("Can't find user by ID " + userId)
        );
        Role role = roleService.findByRoleName(roleName);
        userFromDb.getRoles().add(role);
        return repository.save(userFromDb);
    }
}
