package com.itis.service;

import com.itis.model.News;

import java.util.List;

public interface NewsService {
    List<News> getNews();

    void addNews(News news);
}
