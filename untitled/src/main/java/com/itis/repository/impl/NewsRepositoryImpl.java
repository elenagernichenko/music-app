package com.itis.repository.impl;

import com.itis.model.News;
import com.itis.repository.NewsRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class NewsRepositoryImpl implements NewsRepository {
    private static final String ADD_NEWS =
            "INSERT INTO news (title,content) VALUES(?,?)";

    private static final String GET_NEWS =
            "SELECT * FROM news ORDER BY date DESC LIMIT 5";

    private final JdbcTemplate jdbcTemplate;

    public NewsRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<News> getAllNews() {
        try {
            return jdbcTemplate.query(GET_NEWS, userMapper);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void addNews(News news) {
        jdbcTemplate.update(ADD_NEWS, news.getTitle(), news.getContent());
    }


    private static final RowMapper<News> userMapper = (row, rowNumber) -> {
        News news = new News();
        news.setId(row.getInt("id"));
        news.setDate(row.getString("date"));
        news.setContent(row.getString("content"));
        news.setTitle(row.getString("title"));
        return news;
    };
}
