package com.gmail.smaglenko.atmapp.util.mapper;

import com.gmail.smaglenko.atmapp.dto.UserDto;
import com.gmail.smaglenko.atmapp.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "bankAccounts", ignore = true)
    @Mapping(target = "roles", ignore = true)
    UserDto mapToDto(User user);

    @Mapping(target = "id", ignore = true)
    User mapToModel(UserDto dto);
}
