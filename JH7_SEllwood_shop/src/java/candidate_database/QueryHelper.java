
package candidate_database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class QueryHelper {
    public Object processQuery(String sql, ConnectionPool connectionPool) throws SQLException
    {
        Object results=null;
        Connection connection;
        Statement statement;
        boolean connectionIsStale;
        ResultSet resultSet=null;
        System.out.println(sql);
        int retryCount=0;
        
        do
        {
            connectionIsStale = false; // optimistic assumption
            retryCount += 1;
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            
            try
            {
                resultSet = statement.executeQuery(sql);
                results = processResultSet(resultSet);
                
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
                //free resources
                if (resultSet != null)resultSet.close();
                if (statement != null)statement.close();
                connectionPool.free(connection);
            }
        } while(connectionIsStale);
        
        return results;
        
    }
    public abstract Object processResultSet(ResultSet resultSet)
            throws SQLException;
    
}
