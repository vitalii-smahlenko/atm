package com.gmail.smaglenko.atmapp.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import com.gmail.smaglenko.atmapp.dto.UserDto;
import com.gmail.smaglenko.atmapp.model.User;
import com.gmail.smaglenko.atmapp.model.Role;
import com.gmail.smaglenko.atmapp.service.RoleService;
import org.hamcrest.Matchers;
import org.springframework.security.test.context.support.WithMockUser;
import com.gmail.smaglenko.atmapp.model.Role.RoleName;
import com.gmail.smaglenko.atmapp.service.UserService;
import com.gmail.smaglenko.atmapp.util.mapper.UserDtoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @MockBean
    private UserService userService;
    @MockBean
    private UserDtoMapper userDtoMapper;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RoleService roleService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void addRoleToUser_shouldAddRoleToUser_whenCorrectRequest() throws Exception {
        Long userId = 1L;
        RoleName roleName = RoleName.ADMIN;
        Role role = new Role();
        role.setRoleName(roleName);
        User user = new User();
        user.setId(userId);
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        userDto.getRoles().add(role);

        when(userService.addRoleToUser(userId, roleName)).thenReturn(user);
        when(userDtoMapper.mapToDto(user)).thenReturn(userDto);

        mockMvc.perform(put("/user/add-role-to-user/{userId}/role/{roleName}",
                        userId, roleName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.roles[0].roleName", Matchers.is(roleName.name())));
    }
}
