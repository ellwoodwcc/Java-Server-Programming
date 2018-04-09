
package servlets;

import candidate_database.ConnectionPool;
import candidate_database.Customer;
import candidate_database.Order;
import candidate_database.Order_item;
import candidate_database.ShoppingCartItem;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AdminServlet extends HttpServlet {
//   
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    //String titlepk = null;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        

        try {
            ServletContext servletContext = getServletContext();
            ConnectionPool connectionPool = (ConnectionPool) servletContext.getAttribute("connectionPool");
            HttpSession session = request.getSession(true);
            String action = "";
            System.out.println("action = " + action);
            action = request.getParameter("action");
            System.out.println("action = " + action);
            
            Boolean admin_login = processLogin_Logoff(session, request, connectionPool);
           
            if (admin_login == null)
            {
                // Need to go login or create a new customer account
                String url = "/AdminLogin.jsp";
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
                dispatcher.forward(request, response);
            }
//            <input type="submit" name="action" value="Process Order">
            else if (action != null && action.equals("Fill_by_this_Item"))
            {       
                //getCommittedOrders(session,  connectionPool);//returns orders and customers
                String titlepk = request.getParameter("titlepk");
                Integer quantity1 = ShoppingCartItem.convert2Int(request.getParameter("howmany"));
                System.out.println(titlepk);
                getCommittedOrdersNew(titlepk, quantity1, session , connectionPool);
//String adminbyorders = request.getParameter("adminbyorders");
                //System.out.println("adminbyorders");
                Integer orderitemB = ShoppingCartItem.convert2Int(request.getParameter("orderno"));
                String useridC = request.getParameter("customerid");
                
               
                String date = request.getParameter("orderdate");
                String itemname = request.getParameter("itemname");
                
                String fname = request.getParameter("fname");
                String lname = request.getParameter("lname");
                String addr = request.getParameter("addr");
                String url1 = request.getParameter("url");
                
                //Integer orderpk = ShoppingCartItem.convert2Int(request.getParameter("orderno"));
//                ArrayList<Customer> customers = Customer.query2(titlepk, connectionPool); 
//                session.setAttribute("customers", customers);
//                //ArrayList<ShoppingCartItem> shoppingCart = (ArrayList<ShoppingCartItem>)session.getAttribute("shoppingCart");
                //ArrayList<Order_item> orderitems = Order.query_order(orderpk,  connectionPool);
               // request.setAttribute("orderitemsA", orderitems);
//                request.setAttribute("orderitemB", orderitemB);
//                System.out.println(orderitemB);
//                request.setAttribute("orderdate", date);
//                request.setAttribute("useridC", useridC);
//                System.out.println(useridC);
//                request.setAttribute("quantity1", quantity1);
//                System.out.println(quantity1);
//                request.setAttribute("titlepk", titlepk);
                //request.setAttribute("itempkT", titlepk);
//                request.setAttribute("fname", fname);
               request.setAttribute("itemname", itemname);
               request.setAttribute("url",url1);
               System.out.println(itemname);
//                request.setAttribute("addr", addr);
                 //order_item.web_item = Web_item.getWeb_item(db_web_page_item_fk, connectionPool);
//                // Display contents of the shopping cart
//                //String url = "/LabelAdminPage.jsp";
////             
////            request.setAttribute("pageTitle", pageTitle);
////            request.setAttribute("otherPages", otherPages);
//              request.setAttribute("orders", orders);
////            
            String url = "/AdminbyCust_1.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
            dispatcher.forward(request, response);
            }
            else
            {       
                getCommittedOrders(session,  connectionPool);
                // Display contents of the shopping cart
                String url = "/AdminbyCust.jsp";
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
                dispatcher.forward(request, response);
                
            }
        } catch (SQLException e) {
            System.out.println("AdminServlet SQLException caught: " + e);
            e.printStackTrace(System.out);
            
            request.setAttribute("exception", e);
            String url = "/ExceptionPage.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
            dispatcher.forward(request, response);
        } finally {
            out.close();
        }
    }
    
    private Boolean processLogin_Logoff(HttpSession session, 
            HttpServletRequest request, ConnectionPool connectionPool) throws SQLException
    {
        if ("logoff".equals(request.getParameter("action"))) {
            session.setAttribute("admin_login", null);
        }
        
        Boolean admin_login = (Boolean) session.getAttribute("admin_login");
        if (admin_login == null) {
            String entered_userid = request.getParameter("entered_userid");
            String entered_password = request.getParameter("entered_password");
            
            // Hardcoded userid and password of chasselb ... Did this to simplify
            if ("ad".equals(entered_userid) && "ad".equals(entered_password))
            {
                // Remember that the admin is now logged in.
                admin_login = true; // let automatic boxing happen
                session.setAttribute("admin_login", admin_login);
            }
        }
        
        return admin_login;
    }
    private void getCommittedOrdersNew(String item,Integer quantity1,
            HttpSession session, ConnectionPool connectionPool) throws SQLException
    {
        
        ArrayList<Customer> customers = Customer.query2(item, connectionPool);//returns 
        ArrayList<Order> orders = null;// =Order.query_order(customer.getUserid(),  connectionPool); 
        Customer customer = null;
        
        // After finding all of the customers, we want query each customer's
        // orders and save it in the Customer class. 
        
        for (int i=0; i < customers.size(); i++)
        {
            customer = customers.get(i);
            orders =Order.query_order(customer.getUserid(),  connectionPool);
            customer.setOrders(orders);//ArrayList<Order> orders setting order to the given customer
            
        }
        session.setAttribute("customers", customers);
        session.setAttribute("orders1", orders);
        session.setAttribute("itempk", item);
        session.setAttribute("quantity1", quantity1);
        //request.setAttribute("orders", orders);
        //session.setAttribute("orders", orders);
        //request.setAttribute("orders", orders);
    }
    
    private void getCommittedOrders(
            HttpSession session,  
            ConnectionPool connectionPool) throws SQLException
    {
        Customer customer = null;
        ArrayList<Customer> customers = Customer.query(connectionPool);
        ArrayList<Order> orders = null;// =Order.query_order(customer.getUserid(),  connectionPool); 
        // After finding all of the customers, we want query each customer's
        // orders and save it in the Customer class. 
        
        for (int i=0; i < customers.size(); i++)
        {
            customer = customers.get(i);
            orders =Order.query_order(customer.getUserid(),  connectionPool);
            customer.setOrders(orders);//ArrayList<Order> orders setting order to the given customer
            
        }
        session.setAttribute("customers", customers);
        //session.setAttribute("howmany", quantity);
        //request.setAttribute("orders", orders);
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    

}
