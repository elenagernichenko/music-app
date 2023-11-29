package com.itis.servlet.userServlet;

import com.itis.model.News;
import com.itis.model.Playlist;
import com.itis.model.Track;
import com.itis.model.User;
import com.itis.service.FileService;
import com.itis.service.MusicService;
import com.itis.service.NewsService;
import com.itis.service.UserService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@WebServlet("/music")
@MultipartConfig
@Slf4j
public class MusicServlet extends HttpServlet {
    private UserService userService;
    private MusicService musicService;

    private NewsService newsService;
    private FileService fileService;


    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
        musicService = (MusicService) getServletContext().getAttribute("musicService");
        fileService = (FileService) getServletContext().getAttribute("fileService");
        newsService = (NewsService) getServletContext().getAttribute("newsService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("point music");
        if (!request.getSession().getAttributeNames().hasMoreElements()) {
            response.sendRedirect("/index");
            return;
        }
        User user = (User) request.getSession(false).getAttribute("user");

        Optional<User> userOptional = userService.getUserByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            request.setAttribute("user", userOptional.get());
            request.setAttribute("playList", musicService.getPlaylist(userOptional.get().getId()));
            request.setAttribute("music", musicService.getTracks(userOptional.get().getId()));
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/music.jsp");
            dispatcher.forward(request, response);
        } else {
            log.warn("user not found by point music");
            response.sendRedirect("/index");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        User user = (User) request.getSession(false).getAttribute("user");
        String playList = getTextValue(request.getPart("playList"));
        String trackName = getTextValue(request.getPart("name"));
        String artist = getTextValue(request.getPart("artist"));
        String album = getTextValue(request.getPart("album"));
        String playlistName = getTextValue(request.getPart("playlistName"));
        log.info("add music playListName - {} trackName - {}", playList, trackName);

        Part filePart = request.getPart("file");
        String fileName = getSubmittedFileName(filePart);


        if (fileName != null && fileName.endsWith(".mp3") || fileName.endsWith(".mp4") ||
                fileName.endsWith(".mp2") || fileName.endsWith(".AAC") ||
                fileName.endsWith(".WAV") || fileName.endsWith(".FLAC") ||
                fileName.endsWith(".ALAC") || fileName.endsWith(".DSD")) {

            String uploadPath = getServletContext().getRealPath("") + File.separator + "/storage";

            fileService.saveFile(fileName, filePart, uploadPath);
            Track track = new Track(trackName, artist, album, new Date().toString(), fileName);
            Integer trackId = musicService.addTrack(track);
            Integer playListId;
            if (playList.isEmpty()) {
                playListId = musicService.addPlayList(new Playlist(playlistName, user.getId()));
            } else {
                playListId = Integer.valueOf(playList);
            }

            musicService.addTrackWithUser(user.getId(), trackId);
            musicService.addPlayListTrack(playListId, trackId);
            newsService.addNews(new News("Пользователь: " + user.getUsername() + " добавил запись: ",
                    trackName + " "));
            response.sendRedirect("/music");
            return;
        }
        log.warn("music not audio file");
        request.setAttribute("errorMessage", "Файл должен быть аудиофайлом");
        request.getRequestDispatcher("/jsp/music.jsp").forward(request, response);

    }

    private String getTextValue(Part part) throws IOException {
        // Получение текстового значения из Part
        BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder value = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            value.append(line);
        }
        return value.toString();
    }

    private String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }
}
