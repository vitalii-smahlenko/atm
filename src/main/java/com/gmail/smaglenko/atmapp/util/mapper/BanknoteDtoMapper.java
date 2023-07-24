package com.gmail.smaglenko.atmapp.util.mapper;

import com.gmail.smaglenko.atmapp.dto.BanknoteDto;
import com.gmail.smaglenko.atmapp.model.Banknote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BanknoteDtoMapper {
    BanknoteDto mapToDto(Banknote banknote);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "atm", ignore = true)
    Banknote mapToModel(BanknoteDto dto);
}
