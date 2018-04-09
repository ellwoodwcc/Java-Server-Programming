/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jh5_278_sellwood;

import java.sql.*; 
     
public class SelectJoin {

   public static void main(String args[]) {
       Connection con=MyConnection.getConnection(args);
       String query = "select CreateCities2.CityName, CreateCities2.StateName, CreateStates2.Region, CreateCities2.Population " +
                      "from CreateStates2, CreateCities2 " +
                      "where CreateStates2.StateName = CreateCities2.StateName";
       Statement stmt;
       if (con == null)
       {
           System.out.println("Unable to create connection");
           return;
       }
       
       try {   
           stmt = con.createStatement();                           
   
           ResultSet rs = stmt.executeQuery(query);
           System.out.println("Regional Cities and their populations:");
           while (rs.next()) {
               String city = rs.getString(1);
               String state = rs.getString(2);
               String region = rs.getString(3);
               String pop = rs.getString(4);
               System.out.println("City=" + city + ", State=" + state+ ", Region="+ region + ", Population="+ pop);
           }

           stmt.close();
           con.close();

       } catch(SQLException ex) {
           System.err.print("SQLException: ");
           System.err.println(ex.getMessage());
       }   
   }
}

