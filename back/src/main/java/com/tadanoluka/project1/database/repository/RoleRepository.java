package com.tadanoluka.project1.database.repository;

import com.tadanoluka.project1.database.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findRoleByNameIgnoreCase(String name);
}
