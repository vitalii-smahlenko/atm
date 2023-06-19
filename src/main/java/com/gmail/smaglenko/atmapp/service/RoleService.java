package com.gmail.smaglenko.atmapp.service;

import com.gmail.smaglenko.atmapp.model.Role;
import com.gmail.smaglenko.atmapp.model.Role.RoleName;

public interface RoleService {
    Role add(Role role);

    Role findByRoleName(RoleName roleName);
}
