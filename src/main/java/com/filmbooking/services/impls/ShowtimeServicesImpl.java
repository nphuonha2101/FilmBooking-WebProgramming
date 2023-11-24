package com.filmbooking.services.impls;

import com.filmbooking.dao.IDAO;
import com.filmbooking.dao.ShowtimeDAOImpl;
import com.filmbooking.model.FilmBooking;
import com.filmbooking.model.Showtime;
import com.filmbooking.services.IFilmBookingServices;
import com.filmbooking.services.IGetObjectAndObjectIDService;
import com.filmbooking.services.IShowtimeServices;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShowtimeServicesImpl implements IShowtimeServices, IGetObjectAndObjectIDService<Showtime> {
    private IDAO<Showtime> showtimeDAO;
    private IFilmBookingServices filmBookingServices;

    public ShowtimeServicesImpl() {
        this.showtimeDAO = new ShowtimeDAOImpl();
        filmBookingServices = new FilmBookingServicesImpl(this);
        hideOldShowtime();
    }

    @Override
    public List<Showtime> getAll() {
        return showtimeDAO.getAll();
    }

    @Override
    public Showtime getByID(String id) {
        return showtimeDAO.getByID(id);
    }

    @Override
    public List<Showtime> getByFilmID(String filmID) {
        List<Showtime> result = new ArrayList<>();
        for (Showtime showtime : this.getAll()
        ) {
            if (showtime.getFilmID().equalsIgnoreCase(filmID))
                result.add(showtime);
        }
        return result;
    }

    @Override
    public HashMap<String, Showtime> getShowtimeAndShowtimeID() {
        return this.getObjectAndObjectID(this.getAll());
    }

    @Override
    public void save(Showtime showtime) {
        showtimeDAO.save(showtime);
    }

    @Override
    public void update(Showtime showtime) {
        showtimeDAO.update(showtime);
    }

    @Override
    public void delete(Showtime showtime) {
        List<FilmBooking> filmBookingList = filmBookingServices.getAll();

        filmBookingList.stream().forEach(filmBooking -> {
            if (filmBooking.getShowtimeID().equalsIgnoreCase(showtime.getShowtimeID()))
                filmBookingServices.delete(filmBooking);
        });

        showtimeDAO.delete(showtime);
    }

    @Override
    public void bookSeats(Showtime showtime, String[] seats) {
        showtime.bookSeats(seats);
        update(showtime);
    }

    @Override
    public HashMap<String, Integer> countAvailableSeats() {
        HashMap<String, Integer> result = new HashMap<>();

        for (Showtime showtime : getAll()
        ) {
            int availableSeats = showtime.countAvailableSeats();
            result.put(showtime.getShowtimeID(), availableSeats);
        }
        return result;
    }

    @Override
    public String getObjectID(Showtime obj) {
        return obj.getShowtimeID();
    }

    private void hideOldShowtime() {
        this.getAll().removeIf(showtime -> showtime.getShowtimeDate().isBefore(LocalDateTime.now()));
    }
}
