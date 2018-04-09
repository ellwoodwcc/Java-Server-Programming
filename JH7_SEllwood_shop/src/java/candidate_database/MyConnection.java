package candidate_database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


// Modified to take a remote parameter.  This will determine whether 
// to target russet.wccnet.edu or localhost

public class MyConnection {

    public static Connection getConnection(boolean remote, String userId, String password)
            throws SQLException {

        Connection con = null;
        String urlStr = null;

        try {

            //Load the Driver Class Now
            Class.forName("com.mysql.jdbc.Driver").newInstance();

        } 
        catch (InstantiationException ex) {
            throw new SQLException("com.mysql.jdbc.Driver InstantiationException");
        } 
        catch (IllegalAccessException ex) {
            throw new SQLException("com.mysql.jdbc.Driver IllegalAccessException");
        } 
        catch (ClassNotFoundException ex) {
            throw new SQLException("com.mysql.jdbc.Driver ClassNotFoundException");
        }
        String host = "localhost";
        if (remote) {
            host = "russet.wccnet.edu";
        }

        urlStr = "jdbc:mysql://" + host + "/" + userId
                + "?user=" + userId + "&password=" + password;
        System.out.println("Connecting to : " + urlStr);
        con = DriverManager.getConnection(urlStr);

        return con;
    }
}
