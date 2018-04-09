/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Database_Person;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdbc.ConnectionPool;

public class DB_PersonServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletContext servletContext = getServletContext();
        ConnectionPool connectionPool = (ConnectionPool) servletContext.getAttribute("connectionPool");//pull out connectionpool from session 
//why need to cast this? casting is commonly done with newly craeted object types
        Connection connection ;//comes from connectionpool
        Statement statement1 ;
        String errorMessage = "";//this measn that if error does not occur, we stuff"" so it won't be null
        try {//how is "" different from null? 
            connection = connectionPool.getConnection();
            statement1 = connection.createStatement();//get statement class. this comes from connection clas
//here created statement but what is in it?
            if (statement1 != null && errorMessage.length() == 0) {//how can pass in an abstract statement? doesn't itneed avalue first?
                errorMessage = DB_PersonCollection.update(statement1, request);//sends public static String update
                //why is this a "static" method statement to supply to executeUpdate ex update request= ex person,author
                }//this passes all the logic to class Dbpersoncollection st ;statement1 object/container allows execute update exe query actions
            if (statement1 != null) {//means error message
                statement1.close();
            }//shutting down
            if (connection != null) {
                connectionPool.free(connection);
            }//return connection back to connection pool ??does this ahppen
        } catch (SQLException ex) {
            errorMessage = ex.toString();//gives useful errors
        }
//
        request.setAttribute("errorMessageA", errorMessage);//what is happening here with set attirubte
//stuff any error message into name "errormessage." into teh request object. usually just "" it usually be an emptry string empty string, nothing will show up in jsp
        RequestDispatcher dispatcher =//forward it to the jsp page who controls the html
                getServletContext().getRequestDispatcher("/Db_PersonCollection.jsp");

        dispatcher.forward(request, response);//if it goes here you have made it to the jsp
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);//sends it back up top
    }
}
