package com.itis.util;

import com.itis.model.User;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.HashMap;
import java.util.Map;

public class SqlConstructor {
    public static Map<Integer, Object> sqlParameterSource(User user) {
        StringBuilder sql = new StringBuilder("UPDATE users SET ");
        Map<Integer, Object> map = new HashMap<>();

        MapSqlParameterSource parameters = new MapSqlParameterSource();

        if (user.getBio() != null && !user.getBio().isEmpty()) {
            sql.append("bio = :bio, ");
            parameters.addValue("bio", user.getBio());
        }
        if (user.getMusicPreferences() != null && !user.getMusicPreferences().isEmpty()) {
            sql.append("musicPreferences = :musicPreferences, ");
            parameters.addValue("musicPreferences", user.getMusicPreferences());
        }
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String hashedPassword = user.getPassword();
            sql.append("password = :password, ");
            parameters.addValue("password", hashedPassword);
        }

        int lastCommaIndex = sql.lastIndexOf(",");
        if (lastCommaIndex != -1) {
            sql.delete(lastCommaIndex, sql.length());
        }

        sql.append(" WHERE id = :id");
        parameters.addValue("id", user.getId());
        map.put(1, parameters);
        map.put(2, sql.toString());
        return map;
    }
}
