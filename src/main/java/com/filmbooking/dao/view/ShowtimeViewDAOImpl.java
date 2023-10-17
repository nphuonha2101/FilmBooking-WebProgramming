package com.filmbooking.dao.view;

import com.filmbooking.dao.IDAO;
import com.filmbooking.database.DatabaseServices;
import com.filmbooking.model.view.ShowtimeView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowtimeViewDAOImpl implements IDAO<ShowtimeView> {
    private DatabaseServices databaseServices;
    private List<ShowtimeView> showtimeViewList;
    private static final String TABLE_NAME = "v_showtime_details";

    public ShowtimeViewDAOImpl() {
        showtimeViewList = new ArrayList<>();
        databaseServices = new DatabaseServices();
        databaseServices.connectDatabase();
    }

    @Override
    public List<ShowtimeView> getAll() {
        Connection connection = databaseServices.getConnection();
        String queryGetALl = "SELECT * FROM " + TABLE_NAME;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryGetALl);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {

                String showtimeID = resultSet.getString("showtime_id");
                String filmName = resultSet.getString("film_name");
                String roomName = resultSet.getString("room_name");
                String theaterName = resultSet.getString("theater_name");
                Date showtimeDate = resultSet.getDate("showtime_date");

                ShowtimeView newShowtimeView = new ShowtimeView(showtimeID, filmName, roomName, theaterName, showtimeDate);
                showtimeViewList.add(newShowtimeView);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return showtimeViewList;
    }

    @Override
    public ShowtimeView getByID(String id) {
        getAll();
        for (ShowtimeView showtimeView : showtimeViewList
        ) {
            if (showtimeView.getShowtimeID().equalsIgnoreCase(id)) return showtimeView;
        }
        return null;
    }

    @Override
    public void save(ShowtimeView showtimeView) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(ShowtimeView showtimeView) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(ShowtimeView showtimeView) {
        throw new UnsupportedOperationException();
    }
}