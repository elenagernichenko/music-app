package com.itis.service;

import com.itis.dto.Roles;
import com.itis.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserByUserName(String email);

    Optional<User> getUserByEmail(String email);

    void updateSettings(Map<Integer, Object> map);

    void addUser(User user);

    List<User> getAllUsers();

    List<Roles> getAllRole();

    void changeRole(Integer userId, Integer roleId);
}
