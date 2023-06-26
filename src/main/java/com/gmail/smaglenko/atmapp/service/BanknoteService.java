package com.gmail.smaglenko.atmapp.service;

import com.gmail.smaglenko.atmapp.model.Banknote;
import java.util.List;

public interface BanknoteService {
    Banknote save(int value);

    void saveAll(List<Banknote> banknotes);

    void remove(Long banknoteId);
}
