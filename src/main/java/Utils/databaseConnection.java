package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConnection {
    public static Connection getConnection() {
        Connection connection = null;
        final String url = "jdbc:mysql://gateway01.ap-southeast-1.prod.aws.tidbcloud.com:4000/project_java";
        final String user = "2o222GZzt728Kr7.root";
        final String password = "JELfRjE9oghzIOky";
        try {
            connection = DriverManager.getConnection(url,user,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (connection == null) {
            System.err.println("BadConnecting");
        } else {
            System.out.println("Goodboiz");
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
