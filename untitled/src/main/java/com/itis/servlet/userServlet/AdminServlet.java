package com.itis.servlet.userServlet;

import com.itis.dto.Info;
import com.itis.dto.Roles;
import com.itis.model.Playlist;
import com.itis.model.User;
import com.itis.service.MusicService;
import com.itis.service.UserService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@WebServlet("/administration")
@Slf4j
public class AdminServlet extends HttpServlet {

    private UserService userService;
    private MusicService musicService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
        musicService = (MusicService) getServletContext().getAttribute("musicService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (validateUser(request, response)) {
            return;
        }
        log.info("point administration");
        List<Playlist> playlists = musicService.getAllPlayList();
        List<Info> infoList = musicService.getInfo();
        List<User> userList = userService.getAllUsers();
        List<Roles> rolesList = userService.getAllRole();

        request.setAttribute("users", userList);
        request.setAttribute("infoList", infoList);
        request.setAttribute("playList", playlists);
        request.setAttribute("roles", rolesList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/admin.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (validateUser(request, response)) {
            return;
        }
        String playlistId = request.getParameter("playlistId");
        String trackId = request.getParameter("trackId");
        String userId = request.getParameter("userId");
        String roleId = request.getParameter("roleId");

        if (!Objects.isNull(playlistId)) {
            log.info("send delete PlayList {}",playlistId);
            musicService.deletePlayListById(Integer.valueOf(playlistId));
        }
        if (!Objects.isNull(trackId)) {
            log.info("send delete Track {}",trackId);
            musicService.deleteTrackById(Integer.valueOf(trackId));
        }
        if (!Objects.isNull(userId) && !Objects.isNull(roleId)) {
            log.info("send changeRole: userId - {}  roleId - {}",userId,roleId);
            userService.changeRole(Integer.valueOf(userId), Integer.valueOf(roleId));
        }

        response.sendRedirect("/administration");
    }

    private boolean validateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession(false).getAttribute("user");
        Optional<User> userByEmail = userService.getUserByEmail(user.getEmail());

        if (!userByEmail.isPresent() || !userByEmail.get().getRoleId().equals(2)) {
            response.sendRedirect("/login");
            log.warn("user {} not Admin",user.getId());
            return true;
        }
        return false;
    }

}
