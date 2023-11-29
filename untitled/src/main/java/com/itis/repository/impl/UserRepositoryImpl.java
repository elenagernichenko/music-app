package com.itis.repository.impl;

import com.itis.dto.Roles;
import com.itis.model.User;
import com.itis.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository {
    private static final String UPDATE_TRACKS =
            "UPDATE users SET role_id = ? where id = ?";
    private static final String GET_ALL_ROLES =
            "SELECT * FROM roles";
    private static final String GET_ALL_USERS =
            "SELECT * FROM users";
    private static final String ADD_USER =
            "INSERT INTO users (username, password, email, role_id) VALUES (?, ?, ?, ?)";
    private static final String SQL_SELECT_BY_USERNAME =
            "select * from users where username = ?";

    private static final String SQL_SELECT_BY_EMAIL =
            "select * from users where email = ?";

    private final JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public UserRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public Optional<User> findOneByUserName(String userName) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_USERNAME, userMapper, userName));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> findOneByEmail(String email) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SQL_SELECT_BY_EMAIL, userMapper, email));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateUserSetting(Map<Integer, Object> sourceMap) {
        namedParameterJdbcTemplate.update((String) sourceMap.get(2), (SqlParameterSource) sourceMap.get(1));
    }

    @Override
    public void addUser(User user) {
        jdbcTemplate.update(ADD_USER, user.getUsername(), user.getPassword(), user.getEmail(), user.getRoleId());
    }

    @Override
    public List<User> getAllUsers() {
        try {
            return jdbcTemplate.query(GET_ALL_USERS, userMapper);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Roles> getAllRoles() {
        try {
            return jdbcTemplate.query(GET_ALL_ROLES, rolesMapper);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void changeRole(Integer userId, Integer roleId) {
        jdbcTemplate.update(UPDATE_TRACKS, roleId, userId);
    }

    private static final RowMapper<User> userMapper = (row, rowNumber) -> {
        User user = new User();
        user.setId(row.getInt("id"));
        user.setUsername(row.getString("username"));
        user.setBio(row.getString("bio"));
        user.setEmail(row.getString("email"));
        user.setPassword(row.getString("password"));
        user.setMusicPreferences(row.getString("musicpreferences"));
        user.setAvatarPath(row.getString("avatarpath"));
        user.setRoleId(row.getInt("role_id"));

        return user;
    };
    private static final RowMapper<Roles> rolesMapper = (row, rowNumber) -> {
        Roles roles = new Roles();
        roles.setId(row.getInt("id"));
        roles.setName(row.getString("name"));
        return roles;
    };
}
