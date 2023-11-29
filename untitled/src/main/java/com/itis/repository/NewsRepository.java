package com.itis.repository;

import com.itis.model.News;

import java.util.List;

public interface NewsRepository {
    List<News> getAllNews();

    void addNews(News news);
}
