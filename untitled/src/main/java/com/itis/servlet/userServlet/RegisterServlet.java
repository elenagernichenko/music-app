package com.itis.servlet.userServlet;

import com.itis.model.User;
import com.itis.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register")
@Slf4j
public class RegisterServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession() != null && request.getSession().getAttribute("authenticated") != null) {
            response.sendRedirect("/profile");
            return;
        }
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        int userRoleId = 1;
        log.info("register username - {} email - {}",username,email);

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        userService.addUser(new User(username, hashedPassword, email, userRoleId));

        response.sendRedirect("/index");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("point registration");
        if (req.getSession() != null && req.getSession().getAttribute("authenticated") != null) {
            resp.sendRedirect("/profile");
            return;
        }
        req.getRequestDispatcher("/jsp/register.jsp").forward(req, resp);
    }
}
