/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jh5_278_sellwood;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.*;
import java.util.StringTokenizer;
import jh5_278_sellwood.MyConnection;
import tools.MyInput;
import jh5_278_sellwood.*;
import com.mysql.jdbc.Driver;
class Prepared {
    
    public static void main(String[] args) {
        MyInput in = new MyInput("CreateStates2.sql");
        //Connection con;
        PreparedStatement prepStmt1;
        Statement stmt = null;
        Statement dstmt = null;
        PreparedStatement prepStmt2;
        PreparedStatement prepStmt3;
        Connection con=MyConnection.getConnection(args);
        String createString = "create table CreateStates2 " +
                           "(StateName varchar(15) NOT NULL, " +
                           "Region varchar(15), " +
                           "LargestCity varchar(25), " +
                           "Capital varchar(20), " +
                           "Population int)";
//                           "primary key(COF_NAME), " +
//                           "foreign key(SUP_ID) references SUPPLIERSPK)";
        if (con == null) 
       { 
           System.out.println("Unable to create connection"); 
           return; 
       } 
        try {
// Connecting to : jdbc:mysql://russet.wccnet.edu/sellwood?user=sellwood&password=qBZ38XDAzHwu
//           prepStmt1 = con.prepareStatement(//see 13:11
//                    "drop table CreateStates2");
            //dstmt =(Statement) con.createStatement();
           // stmt.executeUpdate(dropString);
            //con = MyConnection.getConnection(args);
            prepStmt1 = con.prepareStatement(
                    "drop table CreateStates2");
            prepStmt1.executeUpdate();
            stmt  = con.createStatement();
            //? why can't I use prepare statement?
           stmt.executeUpdate(createString);
        //stmt  = con.createStatement();
            
            
            prepStmt3 = con.prepareStatement(//see 13:11
                    "Insert into CreateStates2 values(?,?,?,?,?)");
            String line = in.get();
//prepStmt1.executeUpdate();
            
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
            prepStmt1.close();
             stmt.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("--- SQLException caught:" + e);
        }

    }
}