package com.gmail.smaglenko.atmapp.repository;

import com.gmail.smaglenko.atmapp.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {
}
