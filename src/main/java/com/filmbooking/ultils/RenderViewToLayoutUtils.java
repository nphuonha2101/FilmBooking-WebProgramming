package com.filmbooking.ultils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class RenderViewToLayoutUtils {
    public static void renderView(HttpServletRequest request, HttpServletResponse response, String viewPath, String layoutPath) {
        request.setAttribute("dynamicContents", viewPath);
        try {
            request.getRequestDispatcher(layoutPath).forward(request, response);
        } catch (ServletException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
