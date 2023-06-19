package com.gmail.smaglenko.atmapp.service;

import com.gmail.smaglenko.atmapp.model.User;

public interface AuthenticationService {
    User register(String username, String password);

    User login(String username, String password);
}
