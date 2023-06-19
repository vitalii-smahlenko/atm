package com.gmail.smaglenko.atmapp.security;

import com.gmail.smaglenko.atmapp.model.Role;
import com.gmail.smaglenko.atmapp.model.User;
import com.gmail.smaglenko.atmapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userFromDb = userService.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found by username: " + username)
        );
        return org.springframework.security.core.userdetails.User
                .withUsername(userFromDb.getUsername())
                .password(userFromDb.getPassword())
                .roles(userFromDb.getRoles().stream()
                        .map(role -> role.getRoleName().toString())
                        .toArray(String[]::new))
                .build();
    }
}
