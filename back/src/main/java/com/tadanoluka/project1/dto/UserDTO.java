package com.tadanoluka.project1.dto;


import com.tadanoluka.project1.database.entity.User;
import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String firstname;
    private String lastname;
    private RoleDTO role;

    public UserDTO(User user) {
        this.username = user.getUsername();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.role = new RoleDTO(user.getRole());
    }
}
