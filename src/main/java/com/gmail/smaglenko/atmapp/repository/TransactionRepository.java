package com.gmail.smaglenko.atmapp.repository;

import com.gmail.smaglenko.atmapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
