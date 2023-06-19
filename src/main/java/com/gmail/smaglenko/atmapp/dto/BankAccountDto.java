package com.gmail.smaglenko.atmapp.dto;

import com.gmail.smaglenko.atmapp.model.User;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class BankAccountDto {
    private Long id;
    private User user;
    private BigDecimal balance;
}
