package com.filmbooking.dao;

import com.filmbooking.database.DatabaseConnection;
import com.filmbooking.model.FilmBooking;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FilmBookingDAOImpl implements IDAO<FilmBooking> {
    private static final String TABLE_NAME = "film_booking";
    private final List<FilmBooking> filmBookingList;

    private final DatabaseConnection databaseConnection;

    public FilmBookingDAOImpl() {
        filmBookingList = new ArrayList<>();
        databaseConnection = DatabaseConnection.getInstance();

    }

    @Override
    public List<FilmBooking> getAll() {
        databaseConnection.connect();
        Connection connection = databaseConnection.getConnection();
        String queryGetAll = "SELECT * FROM " + TABLE_NAME;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryGetAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String filmBookingID = resultSet.getString("film_booking_id");
                String username = resultSet.getString("username");
                String showtimeID = resultSet.getString("showtime_id");
                LocalDateTime bookingDate = resultSet.getTimestamp("booking_date").toLocalDateTime();
                String seatsData = resultSet.getString("seats");
                double totalPrice = resultSet.getDouble("total_fee");

                FilmBooking newFilmBooking = new FilmBooking(filmBookingID, username, showtimeID, bookingDate, seatsData,
                        totalPrice);

                filmBookingList.add(0, newFilmBooking);
            }
            resultSet.close();
            preparedStatement.close();
            databaseConnection.close();
            return filmBookingList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FilmBooking getByID(String id) {
        getAll();
        for (FilmBooking filmBooking : filmBookingList
        ) {
            if (id.equalsIgnoreCase(filmBooking.getFilmBookingID())) return filmBooking;
        }
        return null;
    }

    @Override
    public void save(FilmBooking filmBooking) {
        int largestID = this.getAll().isEmpty() ? 0 : Integer.parseInt(this.getAll().get(0).getFilmBookingID());
        for (FilmBooking fb : this.getAll()) {
            if (Integer.parseInt(fb.getFilmBookingID()) > largestID)
                largestID = Integer.parseInt(fb.getFilmBookingID());
        }
        largestID++;
        filmBooking.setFilmBookingID(String.valueOf(largestID));

        databaseConnection.connect();
        Connection connection = databaseConnection.getConnection();
        String queryAdd = "INSERT INTO " + TABLE_NAME + "(film_booking_id, username, showtime_id, booking_date, " +
                "seats, " +
                "total_fee) " +
                "VALUES(?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryAdd);
            preparedStatement.setString(1, String.valueOf(largestID));
            preparedStatement.setString(2, filmBooking.getUsername());
            preparedStatement.setString(3, filmBooking.getShowtimeID());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(filmBooking.getBookingDate()));
            preparedStatement.setString(5, filmBooking.getSeatsData());
            preparedStatement.setDouble(6, filmBooking.getTotalFee());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            databaseConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(FilmBooking filmBooking) {
        databaseConnection.connect();
        Connection connection = databaseConnection.getConnection();

        //showtime_id, username, booking_date, seat, total_price
        String querySet = "UPDATE " + TABLE_NAME
                + " SET showtime_id = ?, username = ?, booking_date = ?, seats = ?, total_fee = ? WHERE " +
                "film_booking_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(querySet);

            preparedStatement.setString(1, filmBooking.getShowtimeID());
            preparedStatement.setString(2, filmBooking.getUsername());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(filmBooking.getBookingDate()));
            preparedStatement.setString(4, filmBooking.getSeatsData());
            preparedStatement.setDouble(5, filmBooking.getTotalFee());

            preparedStatement.setString(6, filmBooking.getFilmBookingID());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            databaseConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(FilmBooking filmBooking) {
        databaseConnection.connect();
        Connection connection = databaseConnection.getConnection();
        String queryDel = "DELETE FROM " + TABLE_NAME + " WHERE film_booking_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryDel);
            preparedStatement.setString(1, filmBooking.getFilmBookingID());

            preparedStatement.executeUpdate();

            preparedStatement.close();
            databaseConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

