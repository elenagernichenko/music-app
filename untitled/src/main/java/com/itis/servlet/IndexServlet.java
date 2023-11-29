package com.itis.servlet;

import com.itis.model.News;
import com.itis.model.Track;
import com.itis.service.MusicService;
import com.itis.service.NewsService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/index")
@Slf4j
public class IndexServlet extends HttpServlet {

    private MusicService musicService;
    private NewsService newsService;

    @Override
    public void init() throws ServletException {
        musicService = (MusicService) getServletContext().getAttribute("musicService");
        newsService = (NewsService) getServletContext().getAttribute("newsService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("point index");
        List<Track> popularTracks = musicService.geOrderTracks();
        List<News> musicNews = newsService.getNews();

        request.setAttribute("popularTracks", popularTracks);
        request.setAttribute("musicNews", musicNews);
        RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
        dispatcher.forward(request, response);
    }


}

