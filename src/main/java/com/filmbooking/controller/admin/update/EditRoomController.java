package com.filmbooking.controller.admin.update;

import com.filmbooking.dao.IDAO;
import com.filmbooking.hibernate.HibernateSessionProvider;
import com.filmbooking.model.Room;
import com.filmbooking.services.IRoomServices;
import com.filmbooking.services.IShowtimeServices;
import com.filmbooking.services.ITheaterServices;
import com.filmbooking.services.impls.RoomServicesImpl;
import com.filmbooking.services.impls.TheaterServicesImpl;
import com.filmbooking.utils.ContextPathUtils;
import com.filmbooking.utils.RenderViewUtils;
import com.filmbooking.utils.StringUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "editRoom", value = "/edit-room")
public class EditRoomController extends HttpServlet {
    private IRoomServices roomServices;
    private ITheaterServices theaterServices;
    private Room editRoom;
    private HibernateSessionProvider hibernateSessionProvider;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        hibernateSessionProvider = new HibernateSessionProvider();
        roomServices = new RoomServicesImpl(hibernateSessionProvider);
        theaterServices = new TheaterServicesImpl(hibernateSessionProvider);

        String roomID = req.getParameter("room-id_hidden");
        editRoom = roomServices.getByRoomID(roomID);

        req.setAttribute("pageTitle", "editRoomTitle");

        req.setAttribute("editRoom", editRoom);

        req.setAttribute("theaters", theaterServices.getAll());

        RenderViewUtils.renderViewToLayout(req, resp,
                ContextPathUtils.getAdminPagesPath("edit-room.jsp"),
                ContextPathUtils.getLayoutPath("master.jsp"));

        hibernateSessionProvider.closeSession();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        hibernateSessionProvider = new HibernateSessionProvider();
        roomServices = new RoomServicesImpl(hibernateSessionProvider);

        String roomName = StringUtils.handlesInputString(req.getParameter("room-name"));
        int seatRows = Integer.parseInt(req.getParameter("seat-rows"));
        int seatCols = Integer.parseInt(req.getParameter("seat-cols"));

        editRoom.setRoomName(roomName);
        editRoom.setSeatRows(seatRows);
        editRoom.setSeatCols(seatCols);

        roomServices.update(editRoom);

        resp.sendRedirect("room-management");

        hibernateSessionProvider.closeSession();
    }

    @Override
    public void destroy() {
        roomServices = null;
        editRoom = null;
        hibernateSessionProvider = null;
    }
}
