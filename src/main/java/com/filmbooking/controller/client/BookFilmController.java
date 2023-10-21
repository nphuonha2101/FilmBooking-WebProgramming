package com.filmbooking.controller.client;

import com.filmbooking.model.Film;
import com.filmbooking.model.Showtime;
import com.filmbooking.model.view.FilmGenreDetailView;
import com.filmbooking.model.view.ShowtimeView;
import com.filmbooking.services.IFilmGenreDetailViewServices;
import com.filmbooking.services.IFilmServices;
import com.filmbooking.services.IShowtimeServices;
import com.filmbooking.services.IShowtimeViewServices;
import com.filmbooking.services.impls.FilmGenreDetailViewServicesImpl;
import com.filmbooking.services.impls.FilmServicesImpl;
import com.filmbooking.services.impls.ShowtimeServicesImpl;
import com.filmbooking.services.impls.ShowtimeViewServicesImpl;
import com.filmbooking.utils.ContextPathUtils;
import com.filmbooking.utils.RedirectPageUtils;
import com.filmbooking.utils.RenderViewUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "bookFilm", value = "/book-film")
public class BookFilmController extends HttpServlet {
    private IFilmServices filmServices;
    private IFilmGenreDetailViewServices filmGenreDetailViewServices;
    private IShowtimeServices showtimeServices;
    private IShowtimeViewServices showtimeViewServices;

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        filmServices = new FilmServicesImpl();
        filmGenreDetailViewServices = new FilmGenreDetailViewServicesImpl();
        showtimeViewServices = new ShowtimeViewServicesImpl();
        showtimeServices = new ShowtimeServicesImpl();

        String filmID = req.getParameter("film-id");

        Film bookedFilm = filmServices.getByFilmID(filmID);

        List<Showtime> showtimeListOfThisFilm = showtimeServices.getByFilmID(filmID);
        List<ShowtimeView> showtimeViewsOfThisFilm = showtimeViewServices.getViewFromShowtimes(showtimeListOfThisFilm);

        // get film genre names
        StringBuilder filmGenreNames = new StringBuilder();
        List<FilmGenreDetailView> genreDetailsOfFilm = filmGenreDetailViewServices.getByFilmID(filmID);

        for (FilmGenreDetailView filmGenreDetailView : genreDetailsOfFilm
        ) {
            if (filmGenreNames.length() > 1)
                filmGenreNames.append(", ").append(filmGenreDetailView.getGenreName());
            else
                filmGenreNames.append(filmGenreDetailView.getGenreName());
        }

        req.setAttribute("sectionTitle", "Thông tin đặt phim");

        req.setAttribute("filmData", bookedFilm);
        req.setAttribute("filmGenreNames", filmGenreNames.toString());
        req.setAttribute("showtimeViewDetails", showtimeViewsOfThisFilm);

        RenderViewUtils.renderViewToLayout(req, resp,
                ContextPathUtils.getClientPagesPath("book-film.jsp"),
                ContextPathUtils.getLayoutPath("master.jsp"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String seats = req.getParameter("seats");
        String showtimeID = req.getParameter("showtime-id");

        System.out.println("Seats: " + seats + "\n" + "Showtime ID: " + showtimeID);

        RedirectPageUtils.redirectPage("book-film", req, resp);
    }

    @Override
    public void destroy() {
        filmServices = null;
        filmGenreDetailViewServices = null;

    }
}
