package com.gmail.smaglenko.atmapp.dto;

import com.gmail.smaglenko.atmapp.model.ATM;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BanknoteDto {
    private Long id;
    private Integer value;
    private ATM atm;
}
