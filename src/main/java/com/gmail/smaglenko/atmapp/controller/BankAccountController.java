package com.gmail.smaglenko.atmapp.controller;

import com.gmail.smaglenko.atmapp.dto.BankAccountDto;
import com.gmail.smaglenko.atmapp.service.BankAccountService;
import com.gmail.smaglenko.atmapp.util.mapper.BankAccountDtoMapper;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank_account")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final BankAccountDtoMapper bankAccountDtoMapper;

    @ApiOperation(value = "Create a bank account.",
            response = BankAccountDto.class)
    @PostMapping("/create/user/{userId}")
    public BankAccountDto create(@PathVariable Long userId) {
        return bankAccountDtoMapper.mapToDto(bankAccountService.create(userId));
    }
}
