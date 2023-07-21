package com.gmail.smaglenko.atmapp.service.impl;

import com.gmail.smaglenko.atmapp.exception.InvalidAmountException;
import com.gmail.smaglenko.atmapp.exception.InvalidBanknoteAmountException;
import com.gmail.smaglenko.atmapp.exception.NotEnoughMoneyException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import com.gmail.smaglenko.atmapp.model.BankAccount;
import com.gmail.smaglenko.atmapp.model.ATM;
import com.gmail.smaglenko.atmapp.model.Banknote;
import com.gmail.smaglenko.atmapp.repository.ATMRepository;
import com.gmail.smaglenko.atmapp.service.BankAccountService;
import com.gmail.smaglenko.atmapp.service.BanknoteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ATMServiceImplTest {
    private static final Long ID = 1L;
    @InjectMocks
    private ATMServiceImpl atmService;
    @Mock
    private ATMRepository repository;
    @Mock
    private BanknoteService banknoteService;
    @Mock
    private BankAccountService bankAccountService;
    private ATM atm;
    private Banknote banknote100;
    private Banknote banknote200;
    private Banknote banknote500;
    private Banknote banknote1000;
    private BankAccount bankAccount;

    @BeforeEach
    void setUp() {
        atm = new ATM();
        banknote100 = new Banknote();
        banknote100.setId(1L);
        banknote100.setValue(100);
        banknote200 = new Banknote();
        banknote200.setId(2L);
        banknote200.setValue(200);
        banknote500 = new Banknote();
        banknote500.setId(3L);
        banknote500.setValue(500);
        banknote1000 = new Banknote();
        banknote1000.setId(4L);
        banknote1000.setValue(1000);
        bankAccount = new BankAccount();
    }

    @Test
    void addBanknotesToATM_shouldWorkWell_withBanknoteAcceptedByATM() {
        List<Banknote> banknotes = List.of(banknote100, banknote200, banknote500);

        when(repository.findById(ID)).thenReturn(Optional.of(atm));

        atmService.addBanknotesToATM(ID, banknotes);

        verify(banknoteService, times(1)).saveAll(banknotes);
        verify(repository, times(1)).save(atm);
    }

    @Test
    void addBanknotesToATM_shouldThrowException_withBanknoteNotAcceptedByATM() {
        List<Banknote> banknotes = List.of(banknote1000);

        when(repository.findById(ID)).thenReturn(Optional.of(atm));

        assertThrows(InvalidAmountException.class,
                () -> atmService.addBanknotesToATM(ID, banknotes)
        );
    }

    @Test
    void deposit_wortWell_withBanknoteAcceptedByATM() {
        List<Banknote> banknotes = List.of(banknote100, banknote200, banknote500);

        when(bankAccountService.findById(ID)).thenReturn(bankAccount);
        when(repository.findById(ID)).thenReturn(Optional.of(atm));

        atmService.deposit(ID, ID, banknotes);

        BigDecimal expectedBalance = BigDecimal.valueOf(100)
                .add(BigDecimal.valueOf(200))
                .add(BigDecimal.valueOf(500));
        verify(bankAccountService, times(1)).update(bankAccount);
        assertEquals(expectedBalance, bankAccount.getBalance());
    }

    @Test
    void deposit_shouldThrowException_withBanknoteNotAcceptedByATM() {
        List<Banknote> banknotes = List.of(banknote1000);

        when(bankAccountService.findById(ID)).thenReturn(bankAccount);
        when(repository.findById(ID)).thenReturn(Optional.of(atm));

        assertThrows(InvalidAmountException.class,
                () -> atmService.deposit(ID, ID, banknotes)
        );
    }

    @Test
    void withdraw_workWell_whenEnoughMoneyInAccount_and_enoughBanknoteInATM() {
        List<Banknote> banknotes = List.of(banknote100, banknote200, banknote500);
        Integer atmBalance = 1000;
        Integer amount = 500;
        bankAccount.setBalance(new BigDecimal(800));
        atm.getBanknotes().addAll(banknotes);

        when(atmService.getATMBalance(ID)).thenReturn(atmBalance);
        when(repository.findById(ID)).thenReturn(Optional.of(atm));
        when(bankAccountService.findById(ID)).thenReturn(bankAccount);

        atmService.withdraw(ID, ID, amount);

        BigDecimal expectedBalance = BigDecimal.valueOf(300);

        verify(bankAccountService, times(1)).update(bankAccount);
        assertEquals(expectedBalance, bankAccount.getBalance());
    }

    @Test
    void withdraw_shouldThrowException_whenNotEnoughMoneyInAccount() {
        List<Banknote> banknotes = List.of(banknote100, banknote200, banknote500);
        Integer atmBalance = 800;
        Integer amount = 800;
        bankAccount.setBalance(new BigDecimal(500));
        atm.getBanknotes().addAll(banknotes);

        when(atmService.getATMBalance(ID)).thenReturn(atmBalance);
        when(repository.findById(ID)).thenReturn(Optional.of(atm));
        when(bankAccountService.findById(ID)).thenReturn(bankAccount);

        assertThrows(NotEnoughMoneyException.class,
                () -> atmService.withdraw(ID, ID, amount)
        );
    }

    @Test
    void withdraw_shouldThrowException_whenThereAreNoNecessaryBanknotesInATM() {
        List<Banknote> banknotes = List.of(banknote200, banknote500);
        Integer atmBalance = 700;
        Integer amount = 600;
        bankAccount.setBalance(new BigDecimal(600));
        atm.getBanknotes().addAll(banknotes);

        when(atmService.getATMBalance(ID)).thenReturn(atmBalance);
        when(repository.findById(ID)).thenReturn(Optional.of(atm));
        when(bankAccountService.findById(ID)).thenReturn(bankAccount);

        assertThrows(InvalidBanknoteAmountException.class,
                () -> atmService.withdraw(ID, ID, amount)
        );
    }

    @Test
    void withdraw_shouldThrowException_whenNotEnoughMoneyInATM() {
        List<Banknote> banknotes = List.of(banknote200, banknote500);
        Integer atmBalance = 700;
        Integer amount = 800;
        bankAccount.setBalance(new BigDecimal(1000));
        atm.getBanknotes().addAll(banknotes);

        when(atmService.getATMBalance(ID)).thenReturn(atmBalance);

        assertThrows(NotEnoughMoneyException.class,
                () -> atmService.withdraw(ID, ID, amount)
        );
    }

    @Test
    void withdraw_shouldThrowException_whenAmountCannotGetFromAvailableBanknotes() {
        List<Banknote> banknotes = List.of(banknote100, banknote200, banknote500, banknote500);
        Integer atmBalance = 1300;
        Integer amount = 450;
        bankAccount.setBalance(new BigDecimal(1000));
        atm.getBanknotes().addAll(banknotes);

        when(atmService.getATMBalance(ID)).thenReturn(atmBalance);
        when(repository.findById(ID)).thenReturn(Optional.of(atm));
        when(bankAccountService.findById(ID)).thenReturn(bankAccount);

        assertThrows(InvalidAmountException.class,
                () -> atmService.withdraw(ID, ID, amount)
        );
    }
}