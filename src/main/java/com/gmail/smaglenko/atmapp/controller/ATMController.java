package com.gmail.smaglenko.atmapp.controller;

import com.gmail.smaglenko.atmapp.dto.ATMDto;
import com.gmail.smaglenko.atmapp.dto.BanknoteDto;
import com.gmail.smaglenko.atmapp.service.ATMService;
import com.gmail.smaglenko.atmapp.service.mapper.ATMDtoMapper;
import com.gmail.smaglenko.atmapp.service.mapper.BanknoteDtoMapper;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atm")
@RequiredArgsConstructor
public class ATMController {
    private final ATMService atmService;
    private final ATMDtoMapper atmDtoMapper;
    private final BanknoteDtoMapper banknoteDtoMapper;

    @ApiOperation(value = "Create an ATM.",
            response = ATMDto.class)
    @PostMapping("/create")
    public ATMDto createAtm(@RequestBody ATMDto dto) {
        return atmDtoMapper.mapToDto(atmService.save(atmDtoMapper.mapToModel(dto)));
    }

    @ApiOperation(value = "Replenish the ATM",
            response = String.class)
    @PostMapping("/add-money/atm/{atmId}")
    public String addMoneyToAtm(@PathVariable(name = "atmId") Long atmId,
                                @RequestBody List<BanknoteDto> banknotes) {
        atmService.addBanknotesToATM(atmId,
                banknotes.stream()
                        .map(banknoteDtoMapper::mapToModel)
                        .collect(Collectors.toList()));
        return "ATM replenished successfully";
    }

    @ApiOperation(value = "Deposit money to a bank account via ATM",
            response = String.class)
    @PutMapping("/deposit/atm/{atmId}/bank-account/{bankAccountId}")
    public String deposit(@PathVariable Long atmId,
                          @PathVariable Long bankAccountId,
                          @RequestBody List<BanknoteDto> banknotes) {
        atmService.deposit(atmId, bankAccountId,
                banknotes.stream()
                        .map(banknoteDtoMapper::mapToModel)
                        .collect(Collectors.toList()));
        return "The operation was successful!";
    }

    @ApiOperation(value = "Withdraw money from bank account via ATM",
            response = String.class)
    @PutMapping("/withdraw/atm/{atmId}/bank-account/{bankAccountId}/amount/{amount}")
    public String withdraw(@PathVariable Long atmId,
                           @PathVariable Long bankAccountId,
                           @PathVariable Integer amount) {
        atmService.withdraw(atmId, bankAccountId, amount);
        return "The operation was successful!";
    }

    @ApiOperation(value = "Get information about which banknotes are in the ATM",
            response = BanknoteDto.class)
    @GetMapping("/get-all-banknotes/{atmId}")
    public List<BanknoteDto> getAllBanknotes(@PathVariable Long atmId) {
        return atmService.getAllBanknotes(atmId).stream()
                .map(banknoteDtoMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
