
package candidate_database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class Customer { 
    public static final String table_name="customers";
    private int order_pk;
    private String userid;  // primary key
    private String first_name;
    private String last_name;
    private String address;
    private String password;
    private int quantity;
    private ArrayList<Order> orders;
    
    Customer(String userid, String first_name, String last_name, String address )
    {
        // For this constructor we set the password = userid
        this(userid, first_name, last_name, userid, address);
        
    }
    public Customer(String userid, String first_name, String last_name, String password, String address)
    {
        this.userid = userid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.password = password;      
    }
    
    public Customer(String userid,String first_name, String last_name, String address,Integer quantity){
        this.userid = userid;
        this.first_name = first_name;
        this.last_name = last_name;
        this.address = address;
        this.quantity = quantity;
    }
    
    public void setOrders(ArrayList<Order> orders)
    {
        this.orders = orders;
    }
//    public int getOrder_pk()
//    {
//        return order_pk;
//    }
    public ArrayList<Order> getOrders()
    {
        return orders;
    }
    public String getUserid()
    {
        return userid;
    }
    public String getFirst_name()
    {
        return first_name;
    }
    public String getLast_name()
    {
        return last_name;
    }
    public String getAddress()
    {
        return address;
    }
    
    public void insert(ConnectionPool connectionPool) throws SQLException
    {
        String sql = "insert into "+ table_name + " values(" + Util.wrapString(userid) +"," + 
                Util.wrapString(first_name) +","+
                Util.wrapString(last_name) +"," +
                Util.wrapString(password) + "," +  
                Util.wrapString(address) + ")";
        Util.executeSql(sql, connectionPool);
    }
    
    public static void createTable(ConnectionPool connectionPool) throws SQLException
    {
        String sql = "create table "+table_name+ " (userid_pk varchar(20) Primary Key, first_name varchar(20), last_name varchar(20), password varchar(20), address varchar(80))";
        Util.executeSql(sql, connectionPool);
    }
    public static void dropTable(ConnectionPool connectionPool) throws SQLException
    {
        Util.dropTable(table_name, connectionPool);
    }
    public static ArrayList<Customer> query(ConnectionPool connectionPool) throws SQLException
    {
        String sql = "select * from "+ table_name ;
                    
        QueryHelper queryHelper = new QueryHelper() {

            @Override
            public Object processResultSet(ResultSet resultSet)
                    throws SQLException {

                ArrayList<Customer> resultsCollected = new ArrayList<Customer>();

                while(resultSet.next())
                {
                    // First column is 1 not 0
                    String db_userid = resultSet.getString(1);
                    String db_first_name = resultSet.getString(2);
                    String db_last_name = resultSet.getString(3);
                    String db_password = resultSet.getString(4);
                    String db_address = resultSet.getString(5);
                    resultsCollected.add (new Customer(db_userid, db_first_name, db_last_name, db_password, db_address));
                }
                return resultsCollected;

            }

        };
        ArrayList<Customer> results = (ArrayList<Customer>) queryHelper.processQuery(sql, connectionPool);        
        return results;
    }
    
    public static Customer getUser(String userId, String password, ConnectionPool connectionPool) throws SQLException
    {
        if (userId==null || password==null)
            return null;
        
        String sql = "select * from "+ table_name +
                " where userid_pk="+Util.wrapString(userId) +" And password="+Util.wrapString(password);
              
        QueryHelper queryHelper = new QueryHelper() {

            @Override
            public Object processResultSet(ResultSet resultSet)
                    throws SQLException {
                  
                Customer customerFound = null;

                while(resultSet.next())
                {
                    // First column is 1 not 0
                    String db_userid = resultSet.getString(1);
                    String db_first_name = resultSet.getString(2);
                    String db_last_name = resultSet.getString(3);
                    String db_password = resultSet.getString(4);
                    String db_address = resultSet.getString(5);
                    if (customerFound != null)
                    {
                        // This should never happen because userid should be unique  ... a primary key
                        throw new SQLException("Customer.getUser non-unique userid .... this should never happen");
                    }
                    customerFound = new Customer(db_userid, db_first_name, db_last_name, db_password, db_address);
                }
                return customerFound;

            }

        };
        Customer customer = (Customer) queryHelper.processQuery(sql, connectionPool);        
        return customer;
    }
 //, order_items.quantity
public static ArrayList<Customer> query2(String web_page_item_pk, ConnectionPool connectionPool) throws SQLException
    {
        String sql = "select customers.userid_pk, customers.first_name, customers.last_name, customers.address, order_items.quantity from customers"
                + " inner join orders"
                + " on customers.userid_pk = orders.userid_fk"
                + " inner join order_items"
                + " on order_items.order_fk = orders.order_pk"
                + " inner join web_items"
                + " on order_items.web_page_item_fk = web_items.web_page_item_pk"
                + " where web_items.web_page_item_pk =" + "'"+web_page_item_pk+ "'";
                    
        QueryHelper queryHelper = new QueryHelper() {

            @Override
            public Object processResultSet(ResultSet resultSet)
                    throws SQLException {

                ArrayList<Customer> resultsCollected = new ArrayList<Customer>();

                while(resultSet.next())
                {
                    // First column is 1 not 0
                    String db_userid = resultSet.getString(1);
                    String db_first_name = resultSet.getString(2);
                    String db_last_name = resultSet.getString(3);
                    String db_address = resultSet.getString(3);
                    String db_quantity = resultSet.getString(4);
                    System.out.println(db_userid+db_first_name + db_last_name+ db_address+db_quantity);
                    resultsCollected.add (new Customer(db_userid,db_first_name, db_last_name, db_address,db_quantity));
                }
                return resultsCollected;

            }

        };
        ArrayList<Customer> results = (ArrayList<Customer>) queryHelper.processQuery(sql, connectionPool);        
        return results;
    }
}