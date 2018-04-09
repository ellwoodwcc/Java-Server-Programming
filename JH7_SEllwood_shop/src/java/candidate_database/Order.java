
package candidate_database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Order {
    public static final String table_name="orders";

    public static ArrayList<Order_item> query_order(Integer orderpk, ConnectionPool connectionPool) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private int order_pk;   // Primary key
    private String userid_fk; // Foreign key to Customers table
    private String date;
    ArrayList<Order_item> order_items=null;
    
    public Order(int order_pk, String userid_fk, String date)
    {
        this.order_pk = order_pk;
        this.userid_fk = userid_fk;
        this.date = date;
    }
    public int getOrder_pk()
    {
        return order_pk;
    }
    public String getUserid_fk()
    {
        return userid_fk;
    }
    public String getDate()
    {
        return date;
    }
    public ArrayList<Order_item> getOrder_items()
    {
        return order_items;
    }
    public void insert(ConnectionPool connectionPool) throws SQLException
    {
        String sql = "insert into "+ table_name + " values(" + order_pk +"," + 
                Util.wrapString(userid_fk) +","+
                Util.wrapString(date) + ")";
        Util.executeSql(sql, connectionPool);
    }
    
    public static void createTable(ConnectionPool connectionPool) throws SQLException
    {
        String sql = "create table "+table_name +" (order_pk int Primary Key , userid_fk varchar(20), date varchar(50))";
        Util.executeSql(sql, connectionPool);
    }
    public static void dropTable(ConnectionPool connectionPool) throws SQLException
    {
        Util.dropTable(table_name, connectionPool);
    }
    public static ArrayList<Order> query_order(String userid, ConnectionPool connectionPool) throws SQLException
    {
        String sql = "select * from "+ table_name +" where userid_fk="+Util.wrapString(userid);
               
        QueryHelper queryHelper = new QueryHelper() {

            @Override
            public Object processResultSet(ResultSet resultSet)
                    throws SQLException {
 
                ArrayList<Order> resultsCollected = new ArrayList<Order>();

                while(resultSet.next())
                {
                    // First column is 1 not 0
                    int db_order_pk = resultSet.getInt(1);
                    String db_userid_fk = resultSet.getString(2);
                    String db_date = resultSet.getString(3);
                    Order order  = new Order(db_order_pk, db_userid_fk, db_date);
                    order.order_items = Order_item.query_order(db_order_pk, connectionPool);

                    resultsCollected.add(order);
                }       
                return resultsCollected;

            }

        };
        ArrayList<Order> results = (ArrayList<Order>) queryHelper.processQuery(sql, connectionPool);        
        
        return results;
    }
    
}
