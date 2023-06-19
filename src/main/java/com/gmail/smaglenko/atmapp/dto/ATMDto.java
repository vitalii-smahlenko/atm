package com.gmail.smaglenko.atmapp.dto;

import com.gmail.smaglenko.atmapp.model.Banknote;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ATMDto {
    private Long id;
    private List<Banknote> banknotes = new ArrayList<>();
}
