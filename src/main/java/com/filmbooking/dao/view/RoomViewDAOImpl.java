package com.filmbooking.dao.view;

import com.filmbooking.dao.IDAO;
import com.filmbooking.database.DatabaseConnection;
import com.filmbooking.model.view.RoomView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomViewDAOImpl implements IDAO<RoomView> {
    private final DatabaseConnection databaseConnection;
    private final List<RoomView> roomViewList;
    private static final String TABLE_NAME = "v_room_details";

    public RoomViewDAOImpl() {
        this.roomViewList = new ArrayList<>();
        this.databaseConnection = DatabaseConnection.getInstance();

    }

    @Override
    public List<RoomView> getAll() {
        databaseConnection.connect();
        Connection connection = databaseConnection.getConnection();
        String queryGetAll = "SELECT * FROM " + TABLE_NAME;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(queryGetAll);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String roomID = resultSet.getString("room_id");
                String roomName = resultSet.getString("room_name");
                int seatRows = resultSet.getInt("seat_rows");
                int seatCols = resultSet.getInt("seat_cols");
                String theaterName = resultSet.getString("theater_name");

                RoomView newRooView = new RoomView(roomID, roomName, seatRows, seatCols, theaterName);
                roomViewList.add(0, newRooView);
            }

            resultSet.close();
            preparedStatement.close();
            databaseConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roomViewList;
    }

    @Override
    public RoomView getByID(String id) {
        getAll();
        for (RoomView roomView :
                roomViewList) {
            if (roomView.getRoomID().equalsIgnoreCase(id)) return roomView;
        }
        return null;
    }

    @Override
    public void save(RoomView roomView) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(RoomView roomView) {
        throw new UnsupportedOperationException();

    }

    @Override
    public void delete(RoomView roomView) {
        throw new UnsupportedOperationException();
    }
}
