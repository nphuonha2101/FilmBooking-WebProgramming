package com.filmbooking.controller.client;

import com.filmbooking.model.User;
import com.filmbooking.services.IUserServices;
import com.filmbooking.services.impls.UserServicesImpl;
import com.filmbooking.utils.ContextPathUtils;
import com.filmbooking.utils.RenderViewUtils;
import com.filmbooking.utils.StringUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
@WebServlet(value = "/change-password")

public class ChangePasswordController extends HttpServlet {
    private IUserServices userServices;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("pageTitle", "Film Booking - Đổi mật khẩu");
        RenderViewUtils.renderViewToLayout(req, resp,
                ContextPathUtils.getClientPagesPath("change-password.jsp"),
                ContextPathUtils.getLayoutPath("master.jsp"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String currentPassword = req.getParameter("current-password");
        String newPassword = StringUtils.handlesInputString(req.getParameter("new-password"));
        String confirmNewPassword = StringUtils.handlesInputString(req.getParameter("confirm-new-password"));

        userServices = new UserServicesImpl();

        HttpSession userLoginSession = req.getSession();
        User loginUsername = (User) userLoginSession.getAttribute("loginUser");

        User foundUser = userServices.getByUsername(loginUsername.getUsername());

        if (foundUser.getUserPassword().equals(StringUtils.generateSHA256String(currentPassword))) {
            if (newPassword.equals(confirmNewPassword)) {
                newPassword = StringUtils.generateSHA256String(newPassword);
                foundUser.setUserPassword(newPassword);

                userServices.update(foundUser);
                req.setAttribute("successfulMessage", "<span class=\"material-symbols-outlined\">\n" +
                        "task_alt\n" +
                        "</span> Mật khẩu của bạn đã được thay đổi");
                render(req,resp);
            } else {
                req.setAttribute("confirmPasswordError", "Mật khẩu xác nhận không khớp!");
                render(req,resp);
            }

            }else{
            req.setAttribute("currentPasswordError", "Mật khẩu hiện tại không đúng!");
            render(req,resp);
            }

        }

    @Override
    public void destroy() {
        userServices = null;
    }

    private void render(HttpServletRequest req, HttpServletResponse resp){
        RenderViewUtils.updateView(req, resp,  ContextPathUtils.getClientPagesPath("change-password.jsp"));
        RenderViewUtils.renderViewToLayout(req, resp,  ContextPathUtils.getClientPagesPath("change-password.jsp"), ContextPathUtils.getLayoutPath("master.jsp"));
    }
}