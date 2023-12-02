package com.filmbooking.controller.admin.update;

import com.filmbooking.hibernate.HibernateSessionProvider;
import com.filmbooking.model.Film;
import com.filmbooking.model.Genre;
import com.filmbooking.services.impls.FilmServicesImpl;
import com.filmbooking.utils.ContextPathUtils;
import com.filmbooking.utils.RenderViewUtils;
import com.filmbooking.utils.StringUtils;
import com.filmbooking.utils.fileUtils.FileUploadUtils;
import com.filmbooking.utils.fileUtils.FileUtils;
import com.filmbooking.utils.uuidUtils.UUIDUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "editFilm", value = "/edit-film")
@MultipartConfig
public class EditFilmController extends HttpServlet {
    private FilmServicesImpl filmServices;
    private Film editFilm;
    private HibernateSessionProvider hibernateSessionProvider;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        hibernateSessionProvider = new HibernateSessionProvider();
        filmServices = new FilmServicesImpl(hibernateSessionProvider);

        String filmId = req.getParameter("film-id_hidden");
        editFilm = filmServices.getByFilmID(filmId);

        req.setAttribute("editFilm", editFilm);

        // retrieve film genres of film
        StringBuilder filmGenreIDs = new StringBuilder();
        List<Genre> filmGenreList = editFilm.getGenreList();

        filmGenreList.stream().forEach(genre -> {
            filmGenreIDs.append(genre.getGenreID());
            filmGenreIDs.append(" ");
        });

        req.setAttribute("filmGenreIDs", filmGenreIDs.toString().trim());

        req.setAttribute("pageTitle", "editFilmTitle");

        RenderViewUtils.renderViewToLayout(req, resp,
                ContextPathUtils.getAdminPagesPath("edit-film.jsp"),
                ContextPathUtils.getLayoutPath("master.jsp"));

//        RenderViewUtils.updateView(req, resp,
//                ContextPathUtils.getLayoutPath("master.jsp"));

        hibernateSessionProvider.closeSession();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        hibernateSessionProvider = new HibernateSessionProvider();
        filmServices = new FilmServicesImpl(hibernateSessionProvider);

        String filmName = StringUtils.handlesInputString(req.getParameter("film-name"));
        double filmPrice = Double.parseDouble(req.getParameter("film-price"));
        String filmDirector = StringUtils.handlesInputString(req.getParameter("director"));
        String filmActors = StringUtils.handlesInputString(req.getParameter("actors"));
        int filmLength = Integer.parseInt(req.getParameter("film-length"));
        String filmDescription = StringUtils.handlesInputString(req.getParameter("film-description"));
        String filmTrailerLink = StringUtils.handlesInputString(req.getParameter("film-trailer-link"));
        String filmImgName = StringUtils.handlesInputString(req.getParameter("film-img-name"));
        String filmGenres = StringUtils.handlesInputString(req.getParameter("genre-ids"));

        String[] filmGenreIDArr = filmGenres.split(" ");

        editFilm.setFilmName(filmName);
        editFilm.setFilmPrice(filmPrice);
        editFilm.setDirector(filmDirector);
        editFilm.setCast(filmActors);
        editFilm.setFilmLength(filmLength);
        editFilm.setFilmDescription(filmDescription);
        editFilm.setFilmTrailerLink(filmTrailerLink);

        // if not change image
        if (filmImgName == null || filmImgName.isEmpty())
            filmServices.update(editFilm, filmGenreIDArr);
        else {
            String uuidFileName = UUIDUtils.generateRandomUUID(filmImgName);
            String filmImgPath = ContextPathUtils.getUploadFileRelativePath(uuidFileName);

            // delete old img file
            File oldFile = new File(FileUtils.getRealWebappPath(req) + editFilm.getImgPath());
            oldFile.delete();
            System.out.println(oldFile.getAbsolutePath());
            // set new img file and upload to server
            editFilm.setImgPath(filmImgPath);
            filmServices.update(editFilm, filmGenreIDArr);
            FileUploadUtils.uploadFile(req, uuidFileName, "upload-img");
        }

        resp.sendRedirect("film-management");

        hibernateSessionProvider.closeSession();

    }

    @Override
    public void destroy() {
        filmServices = null;
        editFilm = null;
        hibernateSessionProvider = null;
    }
}

