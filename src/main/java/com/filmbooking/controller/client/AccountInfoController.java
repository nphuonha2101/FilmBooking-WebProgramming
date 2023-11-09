package com.filmbooking.controller.client;

import com.filmbooking.filters.AuthLoginFilter;
import com.filmbooking.utils.ContextPathUtils;
import com.filmbooking.utils.RenderViewUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/account-info")

public class AccountInfoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("pageTitle", "Thông tin tài khoản");
        RenderViewUtils.renderViewToLayout(req, resp, ContextPathUtils.getClientPagesPath("account-info.jsp"),
                ContextPathUtils.getLayoutPath("master.jsp"));
    }

}
