package com.itis.servlet.filter;

import com.itis.service.UserService;
import com.itis.servlet.security.AuthenticationManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/login")
public class LoginFilter implements Filter {
    private AuthenticationManager authenticationManager;
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.authenticationManager = (AuthenticationManager) filterConfig.getServletContext().getAttribute("authenticateManager");
        this.userService = (UserService) filterConfig.getServletContext().getAttribute("userService");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        if (req.getSession() != null && req.getSession().getAttribute("authenticated") != null) {
            resp.sendRedirect("/profile");
            return;
        }
        if (req.getMethod().equals("POST")) {
            String username = servletRequest.getParameter("username");
            String password = servletRequest.getParameter("password");

            if (authenticationManager.authenticate(username, password)) {
                HttpSession session = req.getSession(true);
                session.setAttribute("authenticated", true);
                session.setAttribute("user", userService.getUserByUserName(servletRequest.getParameter("username")).get());
                resp.sendRedirect("/profile");
            } else {
                resp.sendRedirect("login.jsp?error=true");
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
