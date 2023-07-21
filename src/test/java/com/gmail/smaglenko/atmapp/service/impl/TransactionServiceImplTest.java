package com.gmail.smaglenko.atmapp.service.impl;

import com.gmail.smaglenko.atmapp.exception.NotEnoughMoneyException;
import com.gmail.smaglenko.atmapp.model.BankAccount;
import com.gmail.smaglenko.atmapp.model.Transaction;
import com.gmail.smaglenko.atmapp.repository.TransactionRepository;
import com.gmail.smaglenko.atmapp.service.BankAccountService;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {
    private static final Long SOURCE_ACCOUNT_ID = 1L;
    private static final Long DESTINATION_ACCOUNT_ID = 2L;
    @InjectMocks
    private TransactionServiceImpl transactionService;
    @Mock
    private TransactionRepository repository;
    @Mock
    private BankAccountService bankAccountService;
    private Transaction transaction;
    private BankAccount sourceAccount;
    private BankAccount destinationAccount;

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        sourceAccount = new BankAccount();
        destinationAccount = new BankAccount();
    }

    @Test
    void transferMoney_shouldThrowException_whenNotEnoughMoneyOnSourceAccount() {
        sourceAccount.setBalance(new BigDecimal(400));
        BigDecimal amount = new BigDecimal(500);

        when(bankAccountService.findById(SOURCE_ACCOUNT_ID)).thenReturn(sourceAccount);
        when(bankAccountService.findById(DESTINATION_ACCOUNT_ID)).thenReturn(destinationAccount);

        assertThrows(NotEnoughMoneyException.class,
                () -> transactionService
                        .transferMoney(SOURCE_ACCOUNT_ID, DESTINATION_ACCOUNT_ID, amount)
        );
    }

    @Test
    void transferMoney_shouldWorkWell_whenEnoughMoneyOnSourceAccount() {
        sourceAccount.setBalance(new BigDecimal(1000));
        BigDecimal amount = new BigDecimal(500);

        when(bankAccountService.findById(SOURCE_ACCOUNT_ID)).thenReturn(sourceAccount);
        when(bankAccountService.findById(DESTINATION_ACCOUNT_ID)).thenReturn(destinationAccount);

        BigDecimal expectedDestinationAccountBalance = new BigDecimal(500);
        transactionService.transferMoney(SOURCE_ACCOUNT_ID, DESTINATION_ACCOUNT_ID, amount);

        verify(bankAccountService, times(1)).update(sourceAccount);
        verify(bankAccountService, times(1)).update(destinationAccount);
        assertEquals(expectedDestinationAccountBalance, destinationAccount.getBalance());
    }
}