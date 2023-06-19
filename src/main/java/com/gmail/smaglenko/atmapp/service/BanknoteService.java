package com.gmail.smaglenko.atmapp.service;

import com.gmail.smaglenko.atmapp.model.Banknote;
import java.util.List;

public interface BanknoteService {
    Banknote createBanknote(int value);

    Banknote findByValue(int value);

    void saveAll(List<Banknote> banknotes);
}
