package com.gmail.smaglenko.atmapp.repository;

import com.gmail.smaglenko.atmapp.model.Role;
import com.gmail.smaglenko.atmapp.model.Role.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRolesByRoleName(RoleName roleName);
}
