package com.filmbooking.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class RedirectPageUtils {
    public static void redirectPage(String pageName, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession userSession = req.getSession(false);
        userSession.setAttribute("previousPage", req.getRequestURI());

        resp.sendRedirect(pageName);
    }

    public static void redirectPreviousPageIfExist(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession userSession = req.getSession(false);
        String previousPage = (String) userSession.getAttribute("previousPage");
        if (previousPage != null) {
            resp.sendRedirect(previousPage);
            return;
        }
        resp.sendRedirect("home");
    }
}
