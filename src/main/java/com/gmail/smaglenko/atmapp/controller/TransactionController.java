package com.gmail.smaglenko.atmapp.controller;

import com.gmail.smaglenko.atmapp.service.TransactionService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @PutMapping("/transfer-money"
            + "/from-account/{sourceAccountId}/to-account/{destinationAccountID}/amount/{amount}")
    public String transferMoney(@PathVariable Long sourceAccountId,
                                @PathVariable Long destinationAccountID,
                                @PathVariable BigDecimal amount) {
        transactionService.transferMoney(sourceAccountId, destinationAccountID, amount);
        return "The operation was successful!";
    }
}
