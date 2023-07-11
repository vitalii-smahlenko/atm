package com.gmail.smaglenko.atmapp.service.impl;

import com.gmail.smaglenko.atmapp.model.BankAccount;
import com.gmail.smaglenko.atmapp.model.Transaction;
import com.gmail.smaglenko.atmapp.repository.TransactionRepository;
import com.gmail.smaglenko.atmapp.service.BankAccountService;
import com.gmail.smaglenko.atmapp.service.TransactionService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository repository;
    private final BankAccountService bankAccountService;

    @Override
    @Transactional
    public void transferMoney(Long sourceAccountId, Long destinationAccountId, BigDecimal amount) {
        BankAccount sourceAccount = bankAccountService.findById(sourceAccountId);
        BankAccount destinationAccount = bankAccountService.findById(destinationAccountId);
        if (amount.compareTo(sourceAccount.getBalance()) > 0) {
            throw new RuntimeException("Not enough money to transfer");
        }
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setSourceAccount(sourceAccount);
        transaction.setDestinationAccount(destinationAccount);
        repository.save(transaction);
        sourceAccount.setBalance(sourceAccount.getBalance().subtract(amount));
        destinationAccount.setBalance(destinationAccount.getBalance().add(amount));
        bankAccountService.update(sourceAccount);
        bankAccountService.update(destinationAccount);
    }
}
