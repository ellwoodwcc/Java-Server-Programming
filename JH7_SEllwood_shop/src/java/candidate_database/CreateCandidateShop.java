
package candidate_database;

import java.sql.SQLException;

// This runnable program drops all of the tables and recreates them again with the tables filled in with 
// some initial data. 

public class CreateCandidateShop {
    void dropTables(ConnectionPool connectionPool) throws SQLException
    {
        System.out.println("*********   dropTables ***********");
        WebPage.dropTable(connectionPool);
        Web_item.dropTable(connectionPool);
        Customer.dropTable(connectionPool);
        Order.dropTable(connectionPool);
        Order_item.dropTable(connectionPool);
        
    }
    void createTables(ConnectionPool connectionPool) throws SQLException
    {
         
        System.out.println("*********   createTables ***********");
        WebPage.createTable(connectionPool);
        Web_item.createTable(connectionPool);
        Customer.createTable(connectionPool);
        Order.createTable(connectionPool);
        Order_item.createTable(connectionPool);
    }
    
    void createWebPages(ConnectionPool connectionPool) throws SQLException
    {       
        String[][] web_pages ={
            {"Cruz", "Ted Cruz for President"},
            {"Clinton", "Hillary Clinton for President"},
            {"Christie", "Chris Christie for President"},
            {"Sanders", "Bernie Sanders for President"},
            {"Trump", "Donald Trump for President"},   
            
            
        };
        
        System.out.println("*********   create web_pages ***********");
        for (int i=0; i < web_pages.length; i++)
        {
            WebPage webPage = new WebPage(web_pages[i][0], web_pages[i][1]);//what is happening here?
            webPage.insert(connectionPool);
        }
        
        //Web_item(String web_page_fk, int item_num, int price, String title//only place this construct is called
        Web_item[] web_items ={//filling the array the old-fash way
            new Web_item("cruz", 1, 1780, "A Time for Truth: Reigniting the Promise of America by Ted Cruz"),
            new Web_item("cruz", 2, 4050, "Show your support for Ted Cruz on Game Day"),
            new Web_item("cruz", 3, 1283, "Enjoy yourself and think of Cruz while you hunt"),
            new Web_item("cruz", 4, 1061, "Right Turn for Cruz"),
            
            new Web_item("clinton", 1, 1700, "Hard Choices by Hillary Clinton"),
            new Web_item("clinton", 2, 1899, "Hillary Bandana or Hijab"),
            new Web_item("clinton", 3, 5500, "Hillary Stitched Throw Pillow"),
            new Web_item("clinton", 4, 1255, "Pride for Hillary"),
            
            new Web_item("christie", 1, 100, "Message to stick anywhere there's a detractor"),
            new Web_item("christie", 2, 3599, "Christie shirts for all shapes"),
            new Web_item("christie", 3, 1233,  "Christie Mug best seller"),
            new Web_item("christie", 4, 1183, "Christie Bumper Sticker"),
                       
            new Web_item("sanders", 1, 1100, "The Speech by Bernie Sanders"),
            new Web_item("sanders", 2, 1200, "Shop for Bernie with this Tote Bag"),
            new Web_item("sanders", 3, 2000, "Bibs - now all ages can campaign for Bernie"),
            new Web_item("sanders", 4, 800,  "The pen is mightier than TV ads"),
            
            new Web_item("trump", 1, 1978, "Let your dog Bark for Trump!"),
            new Web_item("trump", 2, 12980, "Share the spirit at all Trump events"),
            new Web_item("trump", 3, 1503, "A sign in every yard for the low energy types"),
            new Web_item("trump", 4, 1213, "For hunting. Believe, Donald knows deer.")
            
            
        }; 
        System.out.println("*********  Creating web_items Items ***********");
        for (int i=0; i < web_items.length; i++)
        {
            web_items[i].insert(connectionPool);//what happens here?
        }
      
    }
    void createUsers(ConnectionPool connectionPool) throws SQLException
    {       
        Customer[] customers = {
            new Customer("reigniting","Tinder","Flintstone","888 Apple Drive Plano, TX 76549"),
            new Customer("gohillary","Dorothy","Premberton","123 Rock Ave. Washington, DC 20037"),
            new Customer("christiehead","Sayid","Likeitis","234 Quarry Road, New Jersey 13575"),           
            new Customer("whatrevolution","Gary","Wage","456 Quarry Road, Burlington, VT 06578"),
            new Customer("americathegreat","Harry","Loud","456 Tentacles Drive, Columbia, SC 43333"),
            new Customer("backtest","Testing","Customer","456 Willow Drive, Columbia, SC 43333")
        };
        
        System.out.println("*********   create customers ***********");
        for (int i=0; i < customers.length; i++)
        {
            customers[i].insert(connectionPool);
        }
        
    }
    void createOrders(ConnectionPool connectionPool) throws SQLException
    {  
        Order[] orders = { 
            new Order(1, "reigniting", "December 1, 2015"),
            new Order(2, "gohillary", "December 3, 2015"),
            new Order(3, "christiehead", "December 2, 2015"),
            new Order(4, "whatrevolution", "November 30, 2015"),   
            new Order(5, "americathegreat", "December 8, 2015"),
            new Order(6, "backtest", "December 5, 2015")    
        };
        
        System.out.println("*********   create orders ***********");
        for (int i=0; i < orders.length; i++)
        {
            orders[i].insert(connectionPool);
        }
        //where does the primary and foreign key get established
        Order_item[] order_items = {
            new Order_item(1, 1, "cruz_1"),
            new Order_item(1, 2, "cruz_2"),
            new Order_item(1, 3, "cruz_3"),
            new Order_item(1, 1, "cruz_4"),
            
            new Order_item(2, 1, "clinton_1"),
            new Order_item(2, 2, "clinton_2"),
            new Order_item(2, 1, "clinton_3"),
            new Order_item(2, 3, "clinton_4"),
            
            new Order_item(3, 1, "christie_1"),
            new Order_item(3, 1, "christie_2"),
            new Order_item(3, 5, "christie_3"),
            new Order_item(3, 1, "christie_4"),
            
            new Order_item(4, 1, "sanders_1"),
            new Order_item(4, 2, "sanders_2"),
            new Order_item(4, 1, "sanders_3"),
            new Order_item(4, 1, "sanders_4"),
            
            new Order_item(5, 1, "trump_1"),
            new Order_item(5, 1, "trump_2"),
            new Order_item(5, 2, "trump_3"),
            new Order_item(5, 1, "trump_4"),
            
            new Order_item(6, 1, "cruz_1"),
            new Order_item(6, 1, "clinton_1"),
            new Order_item(6, 1, "christie_1"),
            new Order_item(6, 1, "sanders_1"),
            
        };
         
        System.out.println("*********   order_items ***********");
        for (int i=0; i < order_items.length; i++)
        {
            order_items[i].insert(connectionPool);
        }
        
        
    }
    void rebuildAll(boolean remote, String userid, String password) throws SQLException
    {
        int initialConnections = 1;
        int maxConnections = 5;
        ConnectionPool connectionPool = new ConnectionPool();
        connectionPool.CreateConnectionPool(remote, userid, password, initialConnections, maxConnections, true);
        
        dropTables(connectionPool);
        createTables(connectionPool);
        createWebPages(connectionPool);
        createUsers(connectionPool);
        createOrders(connectionPool);
    }
    public static void main(String[] args) {
        boolean remote = true;
        
        // I did the following because I had trouble getting command line 
        // arguments set for this program when the overall project type was a WebSite.
        // I was able to get command line arguments set for a normal Java project
        
        
        String[] fakeArgs={"sellwood",  "qBZ38XDAzHwu"};
        args = fakeArgs;    
        
        try
        {
            CreateCandidateShop csdb = new CreateCandidateShop();
            csdb.rebuildAll(remote, args[0], args[1]);
        }
        catch(SQLException e)
        {
            System.out.println("SQLException: "+e);
            e.printStackTrace(System.out);
        }
        
        
    }
    
}
