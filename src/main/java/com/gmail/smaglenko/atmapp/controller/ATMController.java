package com.gmail.smaglenko.atmapp.controller;

import com.gmail.smaglenko.atmapp.dto.ATMDto;
import com.gmail.smaglenko.atmapp.dto.BanknoteDto;
import com.gmail.smaglenko.atmapp.model.Banknote;
import com.gmail.smaglenko.atmapp.service.ATMService;
import com.gmail.smaglenko.atmapp.service.mapper.ATMDtoMapper;
import com.gmail.smaglenko.atmapp.service.mapper.BanknoteDtoMapper;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/atm")
@RequiredArgsConstructor
public class ATMController {
    private final ATMService atmService;
    private final ATMDtoMapper atmDtoMapper;
    private final BanknoteDtoMapper banknoteDtoMapper;

    @PostMapping("/create")
    public ATMDto createAtm(@RequestBody ATMDto dto) {
        return atmDtoMapper.mapToDto(atmService.save(atmDtoMapper.mapToModel(dto)));
    }

    @PostMapping("/add-money")
    public String addMoneyToAtm(@RequestParam Long atmId,
                                @RequestBody List<BanknoteDto> banknotes) {
        atmService.addBanknotesToATM(atmId,
                banknotes.stream()
                        .map(banknoteDtoMapper::mapToModel)
                        .collect(Collectors.toList()));
        return "ATM replenished successfully";
    }

    @PutMapping("/deposit")
    public String deposit(@RequestParam Long atmId,
                          @RequestParam Long bankAccountId,
                          @RequestBody List<Banknote> banknotes) {
        atmService.deposit(atmId, bankAccountId, banknotes);
        return "The operation was successful";
    }

    @PutMapping("/withdraw")
    public String withdraw(@RequestParam Long atmId, @RequestParam Long bankAccountId,
                           @RequestParam Integer amount) {
        atmService.withdraw(atmId, bankAccountId, amount);
        return "The operation was successful";
    }
}
