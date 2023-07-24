package com.gmail.smaglenko.atmapp.util.mapper;

import com.gmail.smaglenko.atmapp.dto.ATMDto;
import com.gmail.smaglenko.atmapp.model.ATM;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ATMDtoMapper {
    ATMDto mapToDto(ATM atm);

    @Mapping(target = "id", ignore = true)
    ATM mapToModel(ATMDto dto);
}
