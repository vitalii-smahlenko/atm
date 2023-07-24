package com.gmail.smaglenko.atmapp.repository;

import com.gmail.smaglenko.atmapp.model.ATM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ATMRepository extends JpaRepository<ATM, Long> {
    @Query("SELECT SUM(b.value) FROM Banknote b INNER JOIN b.atm a WHERE a.id = :atmId")
    Integer getATMBalance(@Param("atmId") Long atmId);
}
