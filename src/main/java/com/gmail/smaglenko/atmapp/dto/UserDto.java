package com.gmail.smaglenko.atmapp.dto;

import com.gmail.smaglenko.atmapp.model.BankAccount;
import com.gmail.smaglenko.atmapp.model.Role;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private Set<Role> roles = new HashSet<>();
    private List<BankAccount> bankAccounts = new ArrayList<>();
}
