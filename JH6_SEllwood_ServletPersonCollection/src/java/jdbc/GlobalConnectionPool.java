/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

/**
 *
 * @author Stephen
 */
import java.sql.SQLException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class GlobalConnectionPool implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();//new
        //servlet context is an object that lives for the duration of the containers existence. global class always there
        ConnectionPool connectionPool = new ConnectionPool();
        //sets up connection pool before anyone logs in

        String userid = servletContext.getInitParameter("db_userid");
        String password = servletContext.getInitParameter("db_password");

        // The following code be configurables in web.xml, but for simplicity I have them hard-coded
        int initialConnections = 3;
        int maxConnections = 20;
        boolean waitIfBusy = true;

        // Initialize the Connection Pool
        try {
            connectionPool.CreateConnectionPool(userid, password,
                    initialConnections, maxConnections, waitIfBusy);
            servletContext.setAttribute("connectionPool", connectionPool);
//this is new thing stores things.this makes connex pool availble to entire "application"?
            System.out.println("GlobalConnectionPool has setup the connection pool");
        } catch (SQLException e) {
            System.out.println("init SQLException caught: " + e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        ServletContext servletContext = sce.getServletContext();
        ConnectionPool connectionPool = (ConnectionPool) servletContext.getAttribute("connectionPool");
        //new. getattribute returns an object. pulls out. need to cast because?
        if (connectionPool != null) {
            connectionPool.closeAllConnections();
            System.out.println("GlobalConnectionPool closed out the connection pool");
        }
    }
}
