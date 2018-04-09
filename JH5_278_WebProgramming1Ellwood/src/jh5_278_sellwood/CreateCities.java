/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jh5_278_sellwood;


//import com.mysql.jdbc.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.StringTokenizer;
import jh5_278_sellwood.MyConnection;
import tools.MyInput;
//import jh5_278_sellwood.*;

class CreateCities {
    
    public static void main(String[] args) {
        MyInput in = new MyInput("CreateCities2.sql");
        //Connection con;
        PreparedStatement prepStmt1;
        Statement stmt = null;
        Statement dstmt = null;
        PreparedStatement prepStmt2;
        PreparedStatement prepStmt3;
        Connection con=MyConnection.getConnection(args);
        String deleteTable = "drop table CreateCities2";
        String createString = //"drop table CreateCities2; " +
                            "create table CreateCities2 " +
                           "(CityName varchar(25) NOT NULL, " +
                           "StateName varchar(15) NOT NULL, " +
                           "Population int, " +
                "foreign key(StateName) references CreateStates2)";
                            
        if (con == null) 
       { 
           System.out.println("Unable to create connection"); 
           return; 
       } 
        try {
//            Stmt1 = con.prepareStatement( //? why can't I use prepare statement?
//                    "drop table CreateCities2");
//            prepStmt1.executeUpdate();
       
            try{
               stmt  = (Statement) con.createStatement();
                 //? why can't I use prepare statement?
                stmt.executeUpdate(createString);//this creates the table
            }catch(SQLException e){
                System.out.println("table already exists");
            }
    
            prepStmt3 = con.prepareStatement(//see 13:11
                    "Insert into CreateCities2 values(?,?,?)");
            String line = in.get();//?what is this doing? getting aline from text file on top of this script
            
            while (line != null) {
                StringTokenizer parse = new StringTokenizer(line, " ,\n");
                int colCount = 1;
                while (parse.hasMoreTokens()) {
                    prepStmt3.setString(colCount++, parse.nextToken());
                    // There are other methods like setInt, etc.
                }
                prepStmt3.executeUpdate();
                line = in.get();
            }

            prepStmt3.close();
            //prepStmt1.close();
             stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("--- SQLException caught:" + e);
        }

    }
}