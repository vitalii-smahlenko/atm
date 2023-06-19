package com.gmail.smaglenko.atmapp.service.impl;

import com.gmail.smaglenko.atmapp.model.Banknote;
import com.gmail.smaglenko.atmapp.repository.BanknoteRepository;
import com.gmail.smaglenko.atmapp.service.BanknoteService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BanknoteServiceImpl implements BanknoteService {
    private final BanknoteRepository repository;

    @Override
    public Banknote createBanknote(int value) {
        Banknote banknote = new Banknote();
        banknote.setValue(value);
        return repository.save(banknote);
    }

    @Override
    public Banknote findByValue(int value) {
        return repository.findByValue(value);
    }

    @Override
    public void saveAll(List<Banknote> banknotes) {
        repository.saveAll(banknotes);
    }
}
