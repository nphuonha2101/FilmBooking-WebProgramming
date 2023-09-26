package com.filmbooking.DAOservices;

import com.filmbooking.databaseConfig.DatabaseServices;
import com.filmbooking.model.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilmDAOServicesImpl implements IFilmDAOServices {
    private DatabaseServices databaseServices;
    private List<Film> filmList;

    public FilmDAOServicesImpl() {
        filmList = new ArrayList<>();

        databaseServices = new DatabaseServices();
        databaseServices.connectDatabase();
        Connection connection = databaseServices.getConnection();

        String queryGetAll = "SELECT * FROM film";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryGetAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String filmID = resultSet.getString("film_id");
                String filmName = resultSet.getString("film_name");
                double filmPrice = Double.parseDouble(resultSet.getString("film_price"));
                String roomId = resultSet.getString("room_id");
                String genre = resultSet.getString("genre");
                String imgPath = resultSet.getString("img_path");

                Film film = new Film(filmID, filmName, filmPrice, roomId, genre, imgPath);

                filmList.add(film);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Film> getAll() {
        return filmList;
    }

    @Override
    public boolean saveFilm(Film film) {
        filmList.add(film);

        Connection connection = databaseServices.getConnection();
        String queryAdd = "INSERT INTO film(film_id, film_name, film_price, room_id,genre, img_path) VALUES(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryAdd);
            preparedStatement.setString(1, film.getFilmID());
            preparedStatement.setString(2, film.getFilmName());
            preparedStatement.setString(3, String.valueOf(film.getPrice()));
            preparedStatement.setString(4, film.getRoomID());
            preparedStatement.setString(5, film.getGenre());
            preparedStatement.setString(6, film.getImgPath());

            return preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
//            databaseServices.disconnectDatabase();
        }
    }

    @Override
    public Film getFilmByID(String filmID) {
        for (Film filmInList : filmList
        ) {
            if (filmInList.getFilmID().equalsIgnoreCase(filmID))
                return filmInList;
        }
        return null;
    }

    @Override
    public List<Film> getFilmByName(String filmName) {
        List<Film> result = new ArrayList<>();
        for (Film filmInList : filmList
        ) {
            if (filmInList.getFilmName().startsWith(filmName))
                result.add(filmInList);
        }
        return result;
    }

    @Override
    public boolean deleteFilm(Film film) {
        if (filmList.contains(film)) {
            filmList.remove(film);

            Connection connection = databaseServices.getConnection();
            String queryDelete = "DELETE FROM film WHERE film_id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(queryDelete);
                preparedStatement.setString(1, film.getFilmID());

                return preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    @Override
    public boolean deleteFilmByFilmID(String filmID) {
        Film foundedFilm = null;
        for (Film filmInList : filmList
        ) {
            if (filmInList.getFilmID().equalsIgnoreCase(filmID)) {
                foundedFilm = filmInList;
                break;
            }

        }

        if (foundedFilm != null) {
            Connection connection = databaseServices.getConnection();
            String queryDelete = "DELETE FROM film WHERE film_id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(queryDelete);
                preparedStatement.setString(1, foundedFilm.getFilmID());

                return preparedStatement.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
}