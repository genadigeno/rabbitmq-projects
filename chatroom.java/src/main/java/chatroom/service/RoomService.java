package chatroom.service;

import chatroom.core.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomService {
    private static RoomService instance;

    private RoomService() { }

    public static RoomService getInstance() {
        if (instance == null) {
            synchronized (RoomService.class) {
                if (instance == null) {
                    instance = new RoomService();
                    return instance;
                }
            }
        }
        return instance;
    }

    public List<String> rooms() {
        List<String> rooms = new ArrayList<>();
        String selectSql = "SELECT r.ROOM_NAME FROM rooms AS r WHERE r.DELETED=0";
        try (ResultSet resultSet = DB.select(selectSql)) {
            while (resultSet.next()) {
                rooms.add(resultSet.getString("ROOM_NAME"));
            }
            return rooms;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean existsRoom(String roomName) throws SQLException {
        return DB.count(roomName) == 1;
    }
}
