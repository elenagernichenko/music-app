package com.itis.servlet.userServlet;

import com.itis.model.User;
import com.itis.service.UserService;
import com.itis.util.SqlConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

@WebServlet("/profile")
@Slf4j
public class ProfileServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("point profile");
        if (!request.getSession().getAttributeNames().hasMoreElements()) {
            response.sendRedirect("/index");
            return;
        }
        User user = (User) request.getSession(false).getAttribute("user");

        Optional<User> userOptional = userService.getUserByEmail(user.getEmail());

        if (userOptional.isPresent()) {
            request.setAttribute("user", userOptional.get());
            RequestDispatcher dispatcher = request.getRequestDispatcher("/jsp/profile.jsp");
            dispatcher.forward(request, response);
        } else {
            log.warn("user not fount by point profile");
            response.sendRedirect("/index");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession(false).getAttribute("user");
        Optional<User> userOptional = userService.getUserByEmail(user.getEmail());

        if (!userOptional.isPresent() || !userOptional.get().getPassword().equals(user.getPassword())) {
            return;
        }
        String bio = req.getParameter("bio");
        String password = req.getParameter("password");
        String musicPreferences = req.getParameter("musicPreferences");
        log.info("change profile bio - {} musicPreferences {}",bio,musicPreferences);

        userOptional.get().setBio(bio);
        userOptional.get().setPassword(password);
        userOptional.get().setMusicPreferences(musicPreferences);

        if (password != null && !password.isEmpty()) {
            String hashpw = BCrypt.hashpw(req.getParameter("password"), BCrypt.gensalt());
            user.setPassword(hashpw);
            req.getSession().removeAttribute("user");
            req.getSession().setAttribute("user", user);
            userOptional.get().setPassword(hashpw);
        }

        Map<Integer, Object> updateUsersSet = SqlConstructor.sqlParameterSource(userOptional.get());

        userService.updateSettings(updateUsersSet);


        resp.sendRedirect("/profile");
    }
}
