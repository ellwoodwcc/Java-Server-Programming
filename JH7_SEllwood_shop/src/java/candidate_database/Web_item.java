package candidate_database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Clock;
import java.util.ArrayList;

public class Web_item {

    private static final String table_name = "web_items";

    private String web_page_fk; // foreign key to WebPage table
    private String title;
    private String web_page_item_pk; // primary key
    private int price;
    private int quantity = 1;

    Web_item(String web_page_fk, int item_num, int price, String title) {
        this.web_page_fk = web_page_fk;
        this.price = price;
        this.title = title;
        web_page_item_pk = web_page_fk + "_" + item_num;//new definition
    }

    Web_item(String web_page_item_pk, String web_page_fk, String title, int price) {
        this.web_page_item_pk = web_page_item_pk;
        this.web_page_fk = web_page_fk;
        this.title = title;
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageUrl() {
        // replace last '_' with '/'
        int index = web_page_item_pk.lastIndexOf('_');
        if (index < 0) {
            return "bad_url for " + web_page_item_pk;
        }
        String url = "web_pages/" + web_page_item_pk.substring(0, index) + "/" + web_page_item_pk.substring(index + 1) + ".png";
        return url;
    }

    public String getWeb_page_fk() {
        return web_page_fk;
    }

    public String getTitle() {
        return title;
    }

    public String getWeb_page_item_pk() {
        return web_page_item_pk;
    }

    public int getPriceValue() {
        return price;
    }

    public String getPrice() {
        return Util.dollarsCents(price);
    }

    public void insert(ConnectionPool connectionPool) throws SQLException {
        String sql = "insert into " + table_name + " values(" + Util.wrapString(web_page_item_pk) + ","
                + Util.wrapString(web_page_fk) + ","
                + Util.wrapString(title) + "," + price + ")";
        Util.executeSql(sql, connectionPool);
    }

    public static void createTable(ConnectionPool connectionPool) throws SQLException {
        String sql = "create table " + table_name + " (web_page_item_pk varchar(20) Primary Key, web_page_fk varchar(20), title varchar(500), price int)";
        Util.executeSql(sql, connectionPool);
    }

    public static void dropTable(ConnectionPool connectionPool) throws SQLException {
        Util.dropTable(table_name, connectionPool);
    }

    public static ArrayList<Web_item> query(String web_page, ConnectionPool connectionPool) throws SQLException {
        String sql = "select * from " + table_name + " where web_page_fk=" + Util.wrapString(web_page);
//how does it know to connect this to web_item.query   BC we hav a string and connection popol    
        System.out.println(sql);//debug
        QueryHelper queryHelper = new QueryHelper() {

            @Override//why do we need this?
            public Object processResultSet(ResultSet resultSet)
                    throws SQLException {
                ArrayList<Web_item> resultsCollected = new ArrayList<Web_item>();

                while (resultSet.next()) {
                    // First column is 1 not 0
                    String db_web_page_item_pk = resultSet.getString(1);
                    String db_web_page_fk = resultSet.getString(2);
                    String db_title = resultSet.getString(3);
                    int db_price = resultSet.getInt(4);
                    resultsCollected.add(new Web_item(db_web_page_item_pk, db_web_page_fk, db_title, db_price));
                }
                return resultsCollected;

            }

        };
        ArrayList<Web_item> results = (ArrayList<Web_item>) queryHelper.processQuery(sql, connectionPool);

        return results;
    }

    public static Web_item getWeb_item(String web_page_item_pk, ConnectionPool connectionPool) throws SQLException {
        String sql = "select * from " + table_name + " where web_page_item_pk=" + Util.wrapString(web_page_item_pk);

        QueryHelper queryHelper = new QueryHelper() {

            @Override
            public Object processResultSet(ResultSet resultSet)
                    throws SQLException {
                Web_item web_item_found = null;
                while (resultSet.next()) {
                    // First column is 1 not 0
                    String db_web_page_item_pk = resultSet.getString(1);
                    String db_web_page_fk = resultSet.getString(2);
                    String db_title = resultSet.getString(3);
                    int db_price = resultSet.getInt(4);
                    if (web_item_found != null) {
                        // This shouldn't happen because web_page_item_pk is a primary key
                        throw new SQLException("Web_item.getWeb_item non-unique web_page_item_pk .... Shouldn't happen");
                    }
                    web_item_found = new Web_item(db_web_page_item_pk, db_web_page_fk, db_title, db_price);//now meets def of web_item near top
                }
                return web_item_found;

            }

        };
        Web_item web_item = (Web_item) queryHelper.processQuery(sql, connectionPool);

        return web_item;
    }

}
