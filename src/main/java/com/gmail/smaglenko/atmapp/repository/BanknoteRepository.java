package com.gmail.smaglenko.atmapp.repository;

import com.gmail.smaglenko.atmapp.model.Banknote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanknoteRepository extends JpaRepository<Banknote, Long> {
}
