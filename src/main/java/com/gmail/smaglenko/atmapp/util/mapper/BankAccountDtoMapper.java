package com.gmail.smaglenko.atmapp.util.mapper;

import com.gmail.smaglenko.atmapp.dto.BankAccountDto;
import com.gmail.smaglenko.atmapp.model.BankAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BankAccountDtoMapper {
    @Mapping(target = "user", ignore = true)
    BankAccountDto mapToDto(BankAccount atm);

    @Mapping(target = "id", ignore = true)
    BankAccount mapToModel(BankAccountDto dto);
}
