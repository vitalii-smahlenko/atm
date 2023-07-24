package com.gmail.smaglenko.atmapp.dto;

import com.gmail.smaglenko.atmapp.model.User;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankAccountDto {
    private Long id;
    private User user;
    private BigDecimal balance;
}
