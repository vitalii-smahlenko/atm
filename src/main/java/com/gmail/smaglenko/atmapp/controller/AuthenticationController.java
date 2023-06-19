package com.gmail.smaglenko.atmapp.controller;

import com.gmail.smaglenko.atmapp.dto.UserDto;
import com.gmail.smaglenko.atmapp.service.AuthenticationService;
import com.gmail.smaglenko.atmapp.service.mapper.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authService;
    private final UserDtoMapper mapper;

    @PostMapping("/register")
    public UserDto register(@RequestBody UserDto dto) {
        return mapper.mapToDto(authService
                .register(dto.getUsername(), dto.getPassword()));
    }

    @PostMapping("/login")
    public UserDto login(@RequestBody UserDto dto) {
        return mapper.mapToDto(authService.login(dto.getUsername(), dto.getPassword()));
    }
}
