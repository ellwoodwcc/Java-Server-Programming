
package candidate_database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//throws SQL exception means that whoever calls this class needs to do a try catch punts the responsibilty
public class WebPage {
    private static final String table_name = "web_pages";//from clemshop.java
    //where does this come from?
    private String web_page_pk; // primary key
    private String title;
    
    public WebPage(String web_page_pk, String title)
    {
        this.web_page_pk= web_page_pk;
        this.title = title;
    }
    public String getWeb_page_pk()
    {
        return web_page_pk;
    }
    public String getTitle()
    {
        return title;
    }
    public void insert(ConnectionPool connectionPool) throws SQLException
    {
        String sql = "insert into "+ table_name + " values(" + 
                Util.wrapString(web_page_pk) +
                    ","+ Util.wrapString(title)+ ")";
        Util.executeSql(sql, connectionPool);
    }
    
    public static void createTable(ConnectionPool connectionPool) 
            throws SQLException
    {
        String sql = "create table "+table_name +
                " (web_page_pk varchar(20) Primary Key, title varchar(80))";
        Util.executeSql(sql, connectionPool);
    }
    public static void dropTable(ConnectionPool connectionPool) 
            throws SQLException
    {
        Util.dropTable(table_name, connectionPool);
    }
    
    public static WebPage getWebPage(String web_page_desired, 
            ConnectionPool connectionPool) throws SQLException
    {
        String sql = "select * from "+ table_name +" where web_page_pk="+
                Util.wrapString(web_page_desired) ;
        System.out.println(sql);
        Connection connection;
        Statement statement;
        boolean connectionIsStale;
        WebPage webPage=null;
        ResultSet resultSet=null;
        int retryCount = 0;
        
        do//runs at least once normally
        {
            connectionIsStale = false; // optimistic assumption
            retryCount += 1;
            connection = connectionPool.getConnection();
            statement = connection.createStatement();
            
            try
            {
                resultSet = statement.executeQuery(sql);//run command!!
                
                while(resultSet.next())
                {
                    // First column is 1 not 0
                    String web_page_pk = resultSet.getString(1); //????what? Is this referring to a column from table
                    String title = resultSet.getString(2);//but title is teh 4th column
                    webPage = new WebPage(web_page_pk, title);
                }
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
        
        return webPage;
    }
    
    public static ArrayList<WebPage> query(ConnectionPool connectionPool) //but there is no String here to connect
            throws SQLException
    {
        String sql = "select * from "+ table_name;
        
        
        QueryHelper queryHelper= new QueryHelper(){

            @Override
            public Object processResultSet(ResultSet resultSet) 
                    throws SQLException
            { 
                ArrayList<WebPage> resultsCollected = new ArrayList<WebPage>();
                while(resultSet.next())
                {
                    // First column is 1 not 0
                    String web_page_pk = resultSet.getString(1);  
                    String title = resultSet.getString(2);
                    resultsCollected.add (new WebPage(web_page_pk, title));
                }
                return resultsCollected;
            }
        
        };
        ArrayList<WebPage> results = (ArrayList<WebPage>)
                queryHelper.processQuery(sql, connectionPool);
        
        return results;
    }
    public static ArrayList<WebPage> queryOthers(String currPage, 
            ConnectionPool connectionPool) throws SQLException
    {
        ArrayList<WebPage> results = query(connectionPool);
        for (int i=0; i < results.size(); i++)
        {
            if (currPage.equals(results.get(i).getWeb_page_pk()))
            {
                results.remove(i);
                break;
            }
        }
        return results;
    }
    
    
    
}
