package com.filmbooking.services;

import com.filmbooking.model.Showtime;
import com.filmbooking.model.view.ShowtimeView;

import java.util.HashMap;
import java.util.List;

public interface IShowtimeViewServices {
    List<ShowtimeView> getAll();

    ShowtimeView getByID(String id);
    List<ShowtimeView> getViewFromShowtimes(List<Showtime> showtimeList);
    HashMap<String, ShowtimeView> getShowtimeViewAndShowtimeID();
}
