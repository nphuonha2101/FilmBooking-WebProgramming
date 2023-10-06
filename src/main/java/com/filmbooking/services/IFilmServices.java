package com.filmbooking.services;

import com.filmbooking.model.Film;

import java.util.List;

public interface IFilmServices {
    List<Film> getAll();
    Film getByFilmID(String id);
    void save(Film film);
    void save(Film film, String ...genres);
    void update(Film film);
    void update(Film film, String ...genres);
    void delete(Film film);

}