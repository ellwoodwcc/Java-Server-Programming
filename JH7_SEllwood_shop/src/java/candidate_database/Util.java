
package candidate_database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class Util {
    
    // For a string s, this routine return 's'
    // If the s contains ', then it replaces it with \'
    public static String wrapString(String s)
    {
        StringBuilder sb = new StringBuilder();
        sb.append('\'');
        if (s != null)
        {
            for (int i=0; i < s.length(); i++)
            {
                char c = s.charAt(i);
                if (c == '\'')
                    sb.append("\\'");
                else
                    sb.append(c);
            }
        }//build string \'books_bargain\'"
        sb.append('\'');
        return sb.toString();
    }
    
    // This is used for non-query sql.  Note that we guarantee that we 
    // close the statement and free the connection no matter what happens
    public static void executeSql(String sql, ConnectionPool connectionPool) throws SQLException
    {
        System.out.println(sql);
        Connection connection;
        Statement statement;
        boolean connectionIsStale;
        int retryCount =0;
        
        do
        {
            connectionIsStale = false; // optimistic assumption 
            retryCount += 1;     
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            try{
                statement.executeUpdate(sql);
            }
            catch (com.mysql.jdbc.exceptions.jdbc4.CommunicationsException e)
            {
                // This is the exception that I get when a MySql Exception
                // has been sitting too long
                System.out.println("Stale Connection? sql="+sql+ "\n error ="+e);
                connectionIsStale = true;
                if (retryCount >= connectionPool.getMaxConnections())
                {
                    // Don't want an infinite loop.  If it's going to work, we shouldn't be 
                    // looping more than the maximum number of Connections
                    throw new SQLException("Retries exceeded the maximum number of connections"+
                            " for dealing with com.mysql.jdbc.exceptions.jdbc4.CommunicationsException");
                }
            }
            finally
            {      
                // free resources
                if (statement != null)statement.close();
                connectionPool.free(connection);
            }
        }  while (connectionIsStale);
    }
  
    
    // We often can get an SQLException for dropping a table if it doesn't 
    // already exist.  So we are catching this exception unlike most other
    // SQLExceptions.    
    // We often can get an SQLException for dropping a table if it doesn't 
    // already exist.  So we are catching this exception unlike most other
    // SQLExceptions.
    public static void dropTable(String table_name, ConnectionPool connectionPool)
    {
        String sql = "drop table "+table_name;
        try{
            Util.executeSql(sql, connectionPool);
        }
        catch (SQLException e)
        {
            System.out.println(table_name +" table probably not present to drop: "+e);
        }
        
    }
    
    // Miscellaneous thing necessary to print out something like:
    // $24.03
    // price is in pennies
    // I wasn't able to find a clean way to do this with String.format
    // I had a problem where price = 2403 would print out as
    // $24.3 with String.format ... still think there must be a way to do it
    // with String.format.
    public static String dollarsCents(int price)
    {
        int cents = price%100;
        int cents_digits = cents % 10;
        int cents_tens = cents / 10;
        int dollars = price/100;
        String price_display = "$"+dollars+".";
        
        if (cents_tens > 0) price_display += cents_tens;
        else price_display += "0";
        
        return price_display + cents_digits;
            
    }
    
}
