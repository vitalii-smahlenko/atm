package com.gmail.smaglenko.atmapp.repository;

import com.gmail.smaglenko.atmapp.model.User;
import com.gmail.smaglenko.atmapp.service.RoleService;
import com.gmail.smaglenko.atmapp.service.UserService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DataJpaTest
class UserRepositoryTest {
    private static final Long USER_ID = 1L;
    private static final String USERNAME = "user";
    @Autowired
    private UserRepository userRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private RoleService roleService;

    @Test
    void findByUsername_shouldFindUserByUsername_whenThisUserExist() {
        User expectedResult = new User();
        expectedResult.setUsername(USERNAME);
        expectedResult.setId(USER_ID);

        User actualResult = userRepository.findByUsername(USERNAME).get();

        assertEquals(expectedResult.getId(), actualResult.getId());
    }
}
