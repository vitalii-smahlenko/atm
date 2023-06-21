package com.gmail.smaglenko.atmapp.service;

import java.math.BigDecimal;

public interface TransactionService {
    void transferMoney(Long sourceAccountId, Long destinationAccountId, BigDecimal amount);
}
