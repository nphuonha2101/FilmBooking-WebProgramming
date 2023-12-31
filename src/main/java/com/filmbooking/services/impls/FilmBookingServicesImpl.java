package com.filmbooking.services.impls;

import com.filmbooking.dao.GenericDAOImpl;
import com.filmbooking.dao.IDAO;
import com.filmbooking.hibernate.HibernateSessionProvider;
import com.filmbooking.model.FilmBooking;
import com.filmbooking.model.User;
import com.filmbooking.services.IFilmBookingServices;

import java.util.List;
import java.util.stream.Collectors;

public class FilmBookingServicesImpl implements IFilmBookingServices {
    private final IDAO<FilmBooking> filmBookingDAO;

    public FilmBookingServicesImpl(HibernateSessionProvider sessionProvider) {
        this.filmBookingDAO = new GenericDAOImpl<>(FilmBooking.class);
        setSessionProvider(sessionProvider);
    }

    public FilmBookingServicesImpl() {
        this.filmBookingDAO = new GenericDAOImpl<>(FilmBooking.class);
    }

    @Override
    public void setSessionProvider(HibernateSessionProvider sessionProvider) {
        filmBookingDAO.setSessionProvider(sessionProvider);
    }

    @Override
    public List<FilmBooking> getAll() {
        return filmBookingDAO.getAll();
    }

    @Override
    public FilmBooking getByFilmBookingID(String id) {
        return filmBookingDAO.getByID(id, true);

    }

    @Override
    public List<FilmBooking> getAllByUser(User user) {
        return this.getAll().stream().filter(filmBooking -> filmBooking.getUser().getUsername().equals(user.getUsername())).collect(Collectors.toList());
    }

    @Override
    public boolean save(FilmBooking filmBooking) {
        return filmBookingDAO.save(filmBooking);
    }

    @Override
    public boolean update(FilmBooking filmBooking) {
        return filmBookingDAO.update(filmBooking);
    }

    @Override
    public boolean delete(FilmBooking filmBooking) {
        return filmBookingDAO.delete(filmBooking);
    }


}
