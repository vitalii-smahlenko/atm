package com.gmail.smaglenko.atmapp.service.impl;

import com.gmail.smaglenko.atmapp.exception.InvalidAmountException;
import com.gmail.smaglenko.atmapp.exception.InvalidBanknoteAmountException;
import com.gmail.smaglenko.atmapp.exception.NotEnoughMoneyException;
import com.gmail.smaglenko.atmapp.model.ATM;
import com.gmail.smaglenko.atmapp.model.BankAccount;
import com.gmail.smaglenko.atmapp.model.Banknote;
import com.gmail.smaglenko.atmapp.repository.ATMRepository;
import com.gmail.smaglenko.atmapp.service.ATMService;
import com.gmail.smaglenko.atmapp.service.BankAccountService;
import com.gmail.smaglenko.atmapp.service.BanknoteService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ATMServiceImpl implements ATMService {
    private final ATMRepository repository;
    private final BanknoteService banknoteService;
    private final BankAccountService bankAccountService;
    private static final Set<Integer> BANKNOTES_ACCEPTED_BY_ATM = Set.of(100, 200, 500);

    @Override
    public ATM save(ATM atm) {
        return repository.save(atm);
    }

    @Override
    public List<Banknote> getAllBanknotes(Long atmId) {
        return findById(atmId).getBanknotes();
    }

    @Override
    public Integer getATMBalance(Long atmId) {
        return repository.getATMBalance(atmId);
    }

    @Override
    @Transactional
    public void addBanknotesToATM(Long atmId, List<Banknote> banknotes) {
        ATM atmFromDb = repository.findById(atmId)
                .orElseThrow(() -> new NoSuchElementException("ATM not found"));
        for (Banknote banknote : banknotes) {
            if (!BANKNOTES_ACCEPTED_BY_ATM.contains(banknote.getValue())) {
                throw new InvalidAmountException("An invalid amount. The amount must be a multiple"
                        + " of 100, 200, 500.");
            }
        }
        banknotes.forEach(b -> b.setAtm(atmFromDb));
        banknoteService.saveAll(banknotes);
        atmFromDb.getBanknotes().addAll(banknotes);
        repository.save(atmFromDb);
    }

    @Override
    @Transactional
    public void deposit(Long atmId, Long bankAccountId, List<Banknote> banknotes) {
        BankAccount bankAccount = bankAccountService.findById(bankAccountId);
        addBanknotesToATM(atmId, banknotes);
        for (Banknote banknote : banknotes) {
            bankAccount.setBalance(bankAccount.getBalance()
                    .add(new BigDecimal(banknote.getValue())));
        }
        bankAccountService.update(bankAccount);
    }

    @Override
    @Transactional
    public void withdraw(Long atmId, Long bankAccountId, Integer amount) {
        Integer atmBalance = getATMBalance(atmId);
        if (atmBalance < amount) {
            throw new NotEnoughMoneyException("Sorry, the transaction could not be completed due to "
                    + "not enough money in the ATM. Insufficient funds, try withdrawing "
                    + "a smaller amount.");
        }
        ATM atm = findById(atmId);
        BankAccount bankAccount = bankAccountService.findById(bankAccountId);
        if (bankAccount.getBalance().intValue() < amount) {
            throw new NotEnoughMoneyException("There is not enough money in the account");
        }
        if (!isAmountMultipleOfAvailableBanknotes(amount)) {
            throw new InvalidAmountException("An invalid amount. The amount must be a multiple"
                    + " of 100, 200, 500.");
        }
        List<Banknote> banknotes = getBanknotesOnAmount(atm.getBanknotes(), amount);
        banknotes.stream().map(Banknote::getId).forEach(banknoteService::remove);
        bankAccount.setBalance(bankAccount.getBalance().subtract(new BigDecimal(amount)));
        bankAccountService.update(bankAccount);
    }

    @Override
    public ATM findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Can't find ATM by ID" + id)
        );
    }

    private List<Banknote> getBanknotesOnAmount(List<Banknote> banknotes, int amount) {
        List<Banknote> result = new ArrayList<>();
        int balanceAmount = amount;
        for (int i = banknotes.size() - 1; i >= 0; i--) {
            int value = banknotes.get(i).getValue();
            if (value <= balanceAmount) {
                result.add(banknotes.get(i));
                balanceAmount -= value;
            }
        }
        if (balanceAmount > 0) {
            throw new InvalidBanknoteAmountException("This amount can't be collected from the banknotes "
                    + "available at the ATM. Try a different amount.");
        }
        return result;
    }

    private boolean isAmountMultipleOfAvailableBanknotes(Integer amount) {
        for (int denomination : BANKNOTES_ACCEPTED_BY_ATM) {
            if (amount % denomination == 0) {
                return true;
            }
        }
        return false;
    }
}
