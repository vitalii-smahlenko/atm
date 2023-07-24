package com.gmail.smaglenko.atmapp.service;

import com.gmail.smaglenko.atmapp.model.BankAccount;

public interface BankAccountService {
    BankAccount create(Long userId);

    BankAccount update(BankAccount bankAccount);

    BankAccount findById(Long bankAccountId);

}
