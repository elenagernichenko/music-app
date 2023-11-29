package com.itis.servlet.context;

import com.itis.repository.*;
import com.itis.repository.impl.MusicRepositoryImpl;
import com.itis.repository.impl.NewsRepositoryImpl;
import com.itis.repository.impl.UserRepositoryImpl;
import com.itis.service.*;
import com.itis.service.impl.FileServiceImpl;
import com.itis.service.impl.MusicServiceImpl;
import com.itis.service.impl.NewsServiceImpl;
import com.itis.service.impl.UserServiceImpl;
import com.itis.servlet.security.AuthenticateManagerImpl;
import com.itis.servlet.security.AuthenticationManager;
import com.itis.util.PopularitySet;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.IOException;
import java.util.Properties;

@WebListener
public class ContextListener implements ServletContextListener {

    private HikariDataSource hikariDataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Properties properties = new Properties();

        try {
            properties.load(com.itis.servlet.context.ContextListener.class.getResourceAsStream("/db.properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setUsername(properties.getProperty("db.username"));
        hikariConfig.setPassword(properties.getProperty("db.password"));
        hikariConfig.setDriverClassName(properties.getProperty("db.driverClassName"));
        hikariConfig.setJdbcUrl(properties.getProperty("db.url"));
        hikariConfig.setMaximumPoolSize(Integer.parseInt(properties.getProperty("db.hikari.maxPoolSize")));

        hikariDataSource = new HikariDataSource(hikariConfig);


        UserRepository userRepository = new UserRepositoryImpl(hikariDataSource);
        MusicRepository musicRepository = new MusicRepositoryImpl(hikariDataSource);
        NewsRepository newsRepository = new NewsRepositoryImpl(hikariDataSource);

        NewsService newsService = new NewsServiceImpl(newsRepository);
        MusicService musicService = new MusicServiceImpl(musicRepository);
        UserService userService = new UserServiceImpl(userRepository);
        FileService fileService = new FileServiceImpl();


        AuthenticationManager authenticationManager = new AuthenticateManagerImpl(userRepository);
        sce.getServletContext().setAttribute("fileService", fileService);
        sce.getServletContext().setAttribute("musicService", musicService);
        sce.getServletContext().setAttribute("userService", userService);
        sce.getServletContext().setAttribute("authenticateManager", authenticationManager);
        sce.getServletContext().setAttribute("newsService", newsService);
        sce.getServletContext().setAttribute("dataSource", hikariDataSource);
        PopularitySet popularitySet = new PopularitySet(musicService);
        popularitySet.setPopularity();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (hikariDataSource != null && !hikariDataSource.isClosed()) {
            hikariDataSource.close();
        }
    }
}
