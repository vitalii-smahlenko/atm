package com.gmail.smaglenko.atmapp.repository;

import com.gmail.smaglenko.atmapp.model.ATM;
import com.gmail.smaglenko.atmapp.model.Banknote;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ATMRepository extends JpaRepository<ATM, Long> {
    @Query("SELECT SUM(b.value) FROM Banknote b INNER JOIN b.atm a WHERE a.id = :atmId")
    Integer getATMBalance(@Param("atmId") Long atmId);

    @Modifying
    @Query("UPDATE ATM SET banknotes = :banknotes WHERE id = :atmId")
    void updateBanknotes(@Param("atmId") Long atmId, @Param("banknotes") List<Banknote> banknotes);
}
