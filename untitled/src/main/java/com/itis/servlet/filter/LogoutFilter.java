package com.itis.servlet.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@WebFilter("/logout")
public class LogoutFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);
        if (req.getRequestURI().equals("/logout")) {
            if (session != null) {
                Objects.requireNonNull(getCookieByName(req)).setValue("");
                Objects.requireNonNull(getCookieByName(req)).setPath("/");
                Objects.requireNonNull(getCookieByName(req)).setMaxAge(0);
                resp.addCookie(getCookieByName(req));
                session.removeAttribute("authenticated");
                session.removeAttribute("user");
                session.invalidate();
                resp.sendRedirect("/index");
                return;
            }
        }
        chain.doFilter(req, resp);
    }

    private Cookie getCookieByName(HttpServletRequest req) {
        Cookie cookie;
        for (Cookie cookies : req.getCookies()) {
            if (cookies.getName().equals("JSESSIONID")) {
                cookie = cookies;
                return cookie;
            }
        }
        return null;
    }
}
