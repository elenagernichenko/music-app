package com.itis.repository;

import com.itis.dto.Roles;
import com.itis.model.User;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findOneByUserName(String userName);

    Optional<User> findOneByEmail(String email);

    void updateUserSetting(Map<Integer, Object> sourceMap);

    void addUser(User user);

    List<User> getAllUsers();

    List<Roles> getAllRoles();

    void changeRole(Integer userId, Integer roleId);
}
