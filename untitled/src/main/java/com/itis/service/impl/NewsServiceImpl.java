package com.itis.service.impl;

import com.itis.model.News;
import com.itis.repository.NewsRepository;
import com.itis.service.NewsService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@Slf4j
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    public NewsServiceImpl(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public List<News> getNews() {
        log.info("NewsServiceImpl getNews");
        return newsRepository.getAllNews();
    }

    @Override
    public void addNews(News news) {
        log.info("NewsServiceImpl addNews");
        newsRepository.addNews(news);
    }
}
