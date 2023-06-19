package com.gmail.smaglenko.atmapp.repository;

import com.gmail.smaglenko.atmapp.model.Banknote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanknoteRepository extends JpaRepository<Banknote, Long> {
    Banknote findByValue(int value);
}
