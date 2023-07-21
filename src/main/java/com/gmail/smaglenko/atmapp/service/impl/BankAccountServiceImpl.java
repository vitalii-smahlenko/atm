package com.gmail.smaglenko.atmapp.service.impl;

import com.gmail.smaglenko.atmapp.model.BankAccount;
import com.gmail.smaglenko.atmapp.model.User;
import com.gmail.smaglenko.atmapp.repository.BankAccountRepository;
import com.gmail.smaglenko.atmapp.service.BankAccountService;
import com.gmail.smaglenko.atmapp.service.UserService;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository repository;
    private final UserService userService;

    @Override
    @Transactional
    public BankAccount create(Long userId) {
        User user = userService.findById(userId);
        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(user);
        bankAccount = repository.save(bankAccount);
        user.getBankAccounts().add(bankAccount);
        return bankAccount;
    }

    @Override
    public BankAccount update(BankAccount bankAccount) {
        return repository.save(bankAccount);
    }

    @Override
    public BankAccount findById(Long bankAccountId) {
        return repository.findById(bankAccountId).orElseThrow(
                () -> new NoSuchElementException("Can't find Bank Account by ID " + bankAccountId)
        );
    }
}
