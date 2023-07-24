package com.gmail.smaglenko.atmapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.gmail.smaglenko.atmapp.service.RoleService;
import com.gmail.smaglenko.atmapp.service.UserService;
import com.gmail.smaglenko.atmapp.model.Role;
import com.gmail.smaglenko.atmapp.model.Role.RoleName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@DataJpaTest
class RoleRepositoryTest {
    private static final Long ROLE_ID = 1L;
    private static final RoleName ROLE_NAME = RoleName.USER;
    @Autowired
    private RoleRepository repository;
    @MockBean
    private UserService userService;
    @MockBean
    private RoleService roleService;

    @Test
    void findRolesByRoleName_shouldFindUserByUsername_whenThisUserExist() {
        Role expectedResult = new Role();
        expectedResult.setId(ROLE_ID);
        expectedResult.setRoleName(ROLE_NAME);

        Role actualResult = repository.findRoleByRoleName(ROLE_NAME);

        assertEquals(expectedResult.getId(), actualResult.getId());
    }
}
