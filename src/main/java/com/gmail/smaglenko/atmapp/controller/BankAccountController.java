package com.gmail.smaglenko.atmapp.controller;

import com.gmail.smaglenko.atmapp.dto.BankAccountDto;
import com.gmail.smaglenko.atmapp.service.BankAccountService;
import com.gmail.smaglenko.atmapp.service.mapper.BankAccountDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank_account")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final BankAccountDtoMapper bankAccountDtoMapper;

    @PostMapping("/create")
    public BankAccountDto create(@RequestParam Long userId) {
        return bankAccountDtoMapper.mapToDto(bankAccountService.create(userId));
    }
}
