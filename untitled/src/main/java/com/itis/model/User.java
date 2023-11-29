package com.itis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
    private String bio;
    private String musicPreferences;
    private String avatarPath;
    private Integer roleId;

    public User(String username, String password, String email, Integer roleId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roleId = roleId;
    }
}
