/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

//import jdbcforvideo.*;
import java.net.URL;  
import java.sql.*;  
       
public class MyConnection {  
  
   public static Connection getConnection(String args[]) {  
         
       Connection con=null; 
       String urlStr=null;
   
             if (args.length < 2 )   
       {   
          System.out.println("You need to enter:");   
           System.out.println("         arg[0] = sellwood, arg[1] = qBZ38XDAzHWu"); 
//           System.out.println("But these were hacked in in MyConnection");
           return con;   
//            args = new String[2];	//this should auto-load you into. The args in the get connection is entered automatically
//            args[0] = "sellwood";
//            args[1] = "qBZ38XDAzHwu";
       }
       try {     
            //Load the Driver Class Now       
             Class.forName("com.mysql.jdbc.Driver").newInstance();   
              //russet used to be "localhost"
            urlStr =   "jdbc:mysql://russet.wccnet.edu/" + args[0] +
                  "?user="+args[0]+ "&password="+args[1];
            System.out.println("Connecting to : " + urlStr);
            con = DriverManager.getConnection (urlStr);    
      
       } 
       catch(SQLException ex) {   
           System.err.println("SQLException("+urlStr+"): " + ex);   
       }  
       catch (Exception ex)
        {
            System.err.println("Exception("+urlStr+"): " + ex);    
        } 
 
       return con;  
   }  
}  