package chatroom.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DB {
    private static final Logger LOGGER = LoggerFactory.getLogger(DB.class);
    private static int connectionCount = 0;
    private static String JDBC_URL = "jdbc:sqlserver://localhost:1433;databaseName=";

    private static String username;
    private static String password;
    private static String databaseName;

    private static Connection connection;
    private PropertiesReader reader;

    static  {
        username = PropertiesReader.getProperty("jdbc.user");
        if (username == null) username = "sa";
        password = PropertiesReader.getProperty("jdbc.password");
        if (password == null) password = "";
        databaseName = PropertiesReader.getProperty("jdbc.databaseName");
        if (databaseName == null) databaseName = "test";
        JDBC_URL += databaseName;
    }

    private static Connection getConnection() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(JDBC_URL, username, password);
            connectionCount++;
            LOGGER.warn("Total created connections: [{}]", connectionCount);//should be replaced by DEBUG
        }
        return connection;
    }

    public static ResultSet select(String sql) throws SQLException {
        return getConnection().createStatement().executeQuery(sql);
    }

    public static int count(String roomName) throws SQLException {
        String countSql = "SELECT count(r.ID) as TOTAL_ROOMS FROM rooms AS r WHERE r.DELETED=0 AND r.ROOM_NAME=?";
        PreparedStatement st = getConnection().prepareStatement(countSql);
        st.setString(1, roomName);
        ResultSet rs = st.executeQuery();
        int num = 0;
        while(rs.next()){
            num = (rs.getInt(1));
        }
        return num;
    }
}
