package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dtbConnection {
    public static Connection getConnection() {
        Connection connection = null;
        final String url = "jdbc:mysql://localhost:3306/project_java";
        final String user = "root";
        final String password = "166131Huy@";
        try {
            connection = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
    public static void closeConnection(Connection c) {
        try {
            if (c != null && !c.isClosed()) {
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
