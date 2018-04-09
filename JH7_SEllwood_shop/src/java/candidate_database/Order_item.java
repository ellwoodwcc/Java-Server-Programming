
package candidate_database;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Order_item {
    public static final String table_name="order_items";
    
    private int order_item_pk;  // primary key
    private int order_fk;    // foreign key to Order table
    private int quantity;
    private String web_page_item_fk;  // foreign key to Web_item table
    private Web_item web_item=null;   // reference to associated Web_item table
    
    public Order_item(int order_fk, int quantity, String web_page_item_fk)
    {
        this(order_fk, quantity, web_page_item_fk, -1);
    }
    Order_item(int order_fk, int quantity, String web_page_item_fk, int order_item_pk)
    {
        this.order_fk = order_fk;
        this.quantity = quantity;
        this.web_page_item_fk = web_page_item_fk;
        this.order_item_pk = order_item_pk;
    }
    public int getQuantity()
    {
        return quantity;
    }
    public Web_item getWeb_item()
    {
        return web_item;
    }
    public int getOrder_item_pk()
    {
        return order_item_pk;
    }
    
    public void insert(ConnectionPool connectionPool) throws SQLException
    {
        String sql = "insert into "+ table_name + " values(" + "null," + order_fk +"," + quantity +"," +
                Util.wrapString(web_page_item_fk) + ")";
        Util.executeSql(sql, connectionPool);
    }
    
    public static void createTable(ConnectionPool connectionPool) throws SQLException
    {
        String sql = "create table "+table_name +" (order_item_pk int Primary Key Auto_Increment, order_fk int, quantity integer, web_page_item_fk varchar(20))";
        Util.executeSql(sql, connectionPool);
    }
    public static void dropTable(ConnectionPool connectionPool) throws SQLException
    {
        Util.dropTable(table_name, connectionPool);
    }
    public static ArrayList<Order_item> query_order(int order_fk, ConnectionPool connectionPool) throws SQLException
    {
        String sql = "select * from "+ table_name +" where order_fk="+order_fk;
            
        QueryHelper queryHelper = new QueryHelper() {

            @Override
            public Object processResultSet(ResultSet resultSet)
                    throws SQLException {
 
                ArrayList<Order_item> resultsCollected = new ArrayList<Order_item>();

                while(resultSet.next())
                {
                    // First column is 1 not 0
                    int db_order_item_pk = resultSet.getInt(1);
                    int db_order_fk = resultSet.getInt(2);
                    int db_quantity = resultSet.getInt(3);
                    String db_web_page_item_fk = resultSet.getString(4);
                    Order_item order_item = new Order_item(db_order_fk, db_quantity, db_web_page_item_fk, db_order_item_pk);
                    order_item.web_item = Web_item.getWeb_item(db_web_page_item_fk, connectionPool);

                    resultsCollected.add(order_item);
                }
                return resultsCollected;

            }

        };
        ArrayList<Order_item> results = (ArrayList<Order_item>) queryHelper.processQuery(sql, connectionPool);        
        
        
        return results;
    }
//    Select Customer.first_name, Customer.last_name, Customer.address,
//from Customer
//inner join Order
//on Customer.userid = Order.userid_fk
//inner join Order_item
//on Order_item.order_fk = Order.order_pk
//inner join Web_item
//on Order_item.web_pate_fk = Web_item.web_page_item_pk
//where Web_item.title = var;
    public static ArrayList<Order_item> query_order2(int web_page_item_pk, ConnectionPool connectionPool) throws SQLException
    {
        String sql = "select Customer.first_name, Customer.last_name, Customer.address, from Customer"
                + "inner join Order"
                + "on Customer.userid = Order.userid_fk"
                + "inner join Order_item"
                + "on Order_item.order_fk = Order.order_pk"
                + "inner join Web_item"
                + "on Order_item.web_pate_fk = Web_item.web_page_item_pk"
                + "where Web_item.web_page_item_pk = " +web_page_item_pk;
        QueryHelper queryHelper = new QueryHelper() {

            @Override
            public Object processResultSet(ResultSet resultSet)
                    throws SQLException {
 
                ArrayList<Order_item> resultsCollected = new ArrayList<Order_item>();

                while(resultSet.next())
                {
                    // First column is 1 not 0
                    int db_order_item_pk = resultSet.getInt(1);
                    int db_order_fk = resultSet.getInt(2);
                    int db_quantity = resultSet.getInt(3);
                    String db_web_page_item_fk = resultSet.getString(4);
                    Order_item order_item = new Order_item(db_order_fk, db_quantity, db_web_page_item_fk, db_order_item_pk);
                    order_item.web_item = Web_item.getWeb_item(db_web_page_item_fk, connectionPool);

                    resultsCollected.add(order_item);
                }
                return resultsCollected;

            }

        };
        ArrayList<Order_item> results = (ArrayList<Order_item>) queryHelper.processQuery(sql, connectionPool);        
        
        
        return results;
    }
    
}
