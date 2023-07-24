package com.gmail.smaglenko.atmapp.service;

import com.gmail.smaglenko.atmapp.model.ATM;
import com.gmail.smaglenko.atmapp.model.Banknote;
import java.util.List;
import org.springframework.data.repository.query.Param;

public interface ATMService {
    ATM save(ATM atm);

    List<Banknote> getAllBanknotes(Long atmId);

    Integer getATMBalance(@Param("atmId") Long atmId);

    void addBanknotesToATM(Long atmId, List<Banknote> banknotes);

    void deposit(Long atmId, Long bankAccountId, List<Banknote> banknotes);

    void withdraw(Long atmId, Long bankAccountId, Integer amount);

    ATM findById(Long id);
}
