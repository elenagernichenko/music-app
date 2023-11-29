package com.itis.service.impl;

import com.itis.dto.Roles;
import com.itis.model.User;
import com.itis.repository.UserRepository;
import com.itis.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserByUserName(String userName) {
        log.info("UserServiceImpl getUserByUserName userName - {}", userName);
        return userRepository.findOneByUserName(userName);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        log.info("UserServiceImpl getUserByEmail email - {}", email);
        return userRepository.findOneByEmail(email);
    }

    @Override
    public void updateSettings(Map<Integer, Object> sourceMap) {
        log.info("UserServiceImpl updateSettings");
        userRepository.updateUserSetting(sourceMap);
    }

    @Override
    public void addUser(User user) {
        log.info("UserServiceImpl addUser user - {}", user.getUsername());
        userRepository.addUser(user);
    }

    @Override
    public List<User> getAllUsers() {
        log.info("UserServiceImpl getAllUsers");
        return userRepository.getAllUsers();
    }

    @Override
    public List<Roles> getAllRole() {
        log.info("UserServiceImpl getAllRole");
        return userRepository.getAllRoles();
    }

    @Override
    public void changeRole(Integer userId, Integer roleId) {
        log.info("UserServiceImpl changeRole userId - {} roleId - {}", userId, roleId);
        userRepository.changeRole(userId, roleId);
    }
}
