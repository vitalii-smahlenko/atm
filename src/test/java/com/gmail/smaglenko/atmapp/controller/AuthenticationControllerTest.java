package com.gmail.smaglenko.atmapp.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.gmail.smaglenko.atmapp.model.User;
import com.gmail.smaglenko.atmapp.service.RoleService;
import com.gmail.smaglenko.atmapp.service.UserService;
import com.gmail.smaglenko.atmapp.service.mapper.UserDtoMapper;
import com.gmail.smaglenko.atmapp.dto.UserDto;
import com.gmail.smaglenko.atmapp.service.AuthenticationService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthenticationController.class)
class AuthenticationControllerTest {
    private static final String USERNAME = "Vitalii";
    private static final String PASSWORD = "1234";
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthenticationService authService;
    @MockBean
    private UserDtoMapper mapper;
    @MockBean
    private UserService userService;
    @MockBean
    private RoleService roleService;
    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setUsername(USERNAME);
        userDto.setPassword(PASSWORD);
        userDto.setId(1L);
        user = new User();
        user.setUsername(USERNAME);
        user.setPassword(PASSWORD);
    }

    @Test
    void register_shouldRegisterUser_whenCorrectRequest() throws Exception {
        when(authService.register(userDto.getUsername(), userDto.getPassword())).thenReturn(user);
        when(mapper.mapToDto(user)).thenReturn(userDto);

        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"Vitalii\", \"password\": \"1234\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)));
    }

    @Test
    void login_shouldLoginUser_whenCorrectRequest() throws Exception {
        when(authService.login(userDto.getUsername(), userDto.getPassword())).thenReturn(user);
        when(mapper.mapToDto(user)).thenReturn(userDto);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\": \"Vitalii\", \"password\": \"1234\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1)));;
    }
}
