package com.tadanoluka.project1.dto;

import com.tadanoluka.project1.database.entity.Role;
import lombok.Data;

@Data
public class RoleDTO {
    private String name;

    public RoleDTO(Role role) {
        this.name = role.getName();
    }
}
