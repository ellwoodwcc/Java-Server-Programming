/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jh5_278_sellwood;

import java.sql.*; 
     
public class DeleteUpdate {

   public static void main(String args[]) {
       Connection con=MyConnection.getConnection(args);
       Statement stmt1;
       Statement stmt2;
       Statement stmt3;
       String query = "select CreateCities2.CityName, CreateCities2.StateName, CreateStates2.Region, CreateCities2.Population " +
                      "from CreateStates2, CreateCities2 " +
                      "where CreateStates2.StateName = CreateCities2.StateName";
       String delete = "DELETE FROM CreateCities2 WHERE CityName='Los_Angelos'";
       String population = "Update CreateCities2 set Population = 90000 where CityName='Indio'";
       if (con == null)
       {
           System.out.println("Unable to create connection");
           return;
       }
       
       try {   
           stmt1 = con.createStatement();                           
   
           ResultSet rs = stmt1.executeQuery(query);
           System.out.println("Regional Cities and their populations:");
           while (rs.next()) {
               String city = rs.getString(1);
               String state = rs.getString(2);
               String region = rs.getString(3);
               String pop = rs.getString(4);
               System.out.println("City=" + city + ", State=" + state+ ", Region="+ region + ", Population="+ pop);
           }
           //DELETE top city
           stmt2= con.createStatement();
           stmt2.executeUpdate(delete);
           //Update pop of 2nd city in table
           stmt3= con.createStatement();
           stmt3.executeUpdate(population);
          //repeat the Join query
           ResultSet rs2 = stmt1.executeQuery(query);
            int retval = stmt1.executeUpdate("delete from cities where CityName='San Francisco' ");
           System.out.println(retval);
           System.out.println("****************************************************");
           System.out.println("After deleting LA and updating the population of Indio");
           System.out.println("****************************************************");
           while (rs2.next()) {
               String city = rs2.getString(1);
               String state = rs2.getString(2);
               String region = rs2.getString(3);
               String pop = rs2.getString(4);
               System.out.println("City=" + city + ", State=" + state+ ", Region="+ region + ", Population="+ pop);
           }
           stmt1.close();
           stmt2.close();
           stmt3.close();
           con.close();

       } catch(SQLException ex) {
           System.err.print("SQLException: ");
           System.err.println(ex.getMessage());
       }
       
   }
}

