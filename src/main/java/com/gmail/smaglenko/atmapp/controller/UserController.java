package com.gmail.smaglenko.atmapp.controller;

import com.gmail.smaglenko.atmapp.dto.UserDto;
import com.gmail.smaglenko.atmapp.model.Role.RoleName;
import com.gmail.smaglenko.atmapp.service.UserService;
import com.gmail.smaglenko.atmapp.service.mapper.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserDtoMapper userDtoMapper;

    @PutMapping("/add-role-to-user/{userId}/role/{roleName}")
    public UserDto addRoleToUser(@PathVariable Long userId,
                                 @PathVariable RoleName roleName) {
        return userDtoMapper.mapToDto(userService.addRoleToUser(userId, roleName));
    }
}
