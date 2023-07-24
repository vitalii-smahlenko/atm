package com.gmail.smaglenko.atmapp.repository;

import com.gmail.smaglenko.atmapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
