package com.gmail.smaglenko.atmapp.service.impl;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import com.gmail.smaglenko.atmapp.model.Role;
import com.gmail.smaglenko.atmapp.model.Role.RoleName;
import com.gmail.smaglenko.atmapp.model.User;
import com.gmail.smaglenko.atmapp.service.RoleService;
import com.gmail.smaglenko.atmapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {
    private static final Long ID = 1L;
    private static final String USERNAME = "Vitalii";
    private static final String PASSWORD = "1234";
    private static final RoleName ROLE_NAME = RoleName.USER;
    @InjectMocks
    private AuthenticationServiceImpl authenticationService;
    @Mock
    private UserService userService;
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoder passwordEncoder;
    private Role role;
    private User expected;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId(ID);
        role.setRoleName(ROLE_NAME);
        expected = new User();
        expected.setUsername(USERNAME);
        expected.setPassword(PASSWORD);
    }

    @Test
    void register_shouldWorkWell_whenCorrectCredentials() {
        when(roleService.findByRoleName(RoleName.USER)).thenReturn(role);
        when(userService.save(any())).thenReturn(expected);

        User actual = authenticationService.register(USERNAME, PASSWORD);

        assertEquals(expected, actual);
    }

    @Test
    void register_shouldThrowException_whenUserWithThatUsernameExist() {
        when(userService.findByUsername(USERNAME)).thenReturn(Optional.of(expected));

        assertThrows(RuntimeException.class,
                () -> authenticationService.register(USERNAME, PASSWORD)
        );
    }

    @Test
    void login_shouldWorkWell_whenCorrectCredentials() {
        when(userService.findByUsername(USERNAME)).thenReturn(Optional.of(expected));

        User actual = authenticationService.login(USERNAME, PASSWORD);

        assertEquals(expected, actual);
    }

    @Test
    void login_shouldThrowsException_whenIncorrectCredentials() {
        assertThrows(RuntimeException.class,
                () -> authenticationService.login(USERNAME, PASSWORD)
        );
    }
}