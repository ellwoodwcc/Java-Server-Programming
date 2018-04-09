
package servlets;

import candidate_database.ShoppingCartItem;
import candidate_database.ConnectionPool;
import candidate_database.Customer;
import candidate_database.Order;
import candidate_database.Order_item;
import candidate_database.Util;
import candidate_database.Web_item;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class CheckoutServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    int order_next_primary_key=-1;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            ServletContext servletContext = getServletContext();
            ConnectionPool connectionPool = (ConnectionPool) servletContext.getAttribute("connectionPool");
            HttpSession session = request.getSession(true);
            
            Customer customer = processLogin_Logoff(session, request, connectionPool);
           
            if (customer == null)
            {
                // Need to go login or create a new customer account
                String url = "/LoginCustomer.jsp";
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
                dispatcher.forward(request, response);
            }
            else
            {       
                // Do calculations for checkout purposes and store them 
                // for the JSP page to print out.
                getAndProcessShoppingCart(session,  request, customer, connectionPool);
                
                // This page also lists out the contents of previously 
                // committed orders
                getCommittedOrders(session, customer, connectionPool);
                
                // Display contents of the shopping cart and committed orders
                String url = "/Checkout.jsp";
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
                dispatcher.forward(request, response);
                
            }
        } catch (SQLException e) {
            System.out.println("CheckouServlet- SQLException caught: " + e);
            e.printStackTrace(System.out);
            
            request.setAttribute("exception", e);
            String url = "/ExceptionPage.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
            dispatcher.forward(request, response);
        } finally {
            out.close();
        }
    }
    
    private Customer processLogin_Logoff(HttpSession session, 
            HttpServletRequest request, ConnectionPool connectionPool) throws SQLException
    {
        if ("logoff".equals(request.getParameter("action"))) {
            session.setAttribute("customer", null);
            session.setAttribute("shoppingCart", null);
        }
        if ("Create New Account".equals(request.getParameter("action")))
        {
            String userid=request.getParameter("userid");
            String first_name = request.getParameter("first_name");
            String last_name = request.getParameter("last_name");
            String address = request.getParameter("address");
            String password = request.getParameter("password");
            String second_password = request.getParameter("second_password");
            if (password.equals(second_password))
            {
                Customer newCustomer = new Customer(userid, first_name, last_name, password,  address);
                newCustomer.insert(connectionPool);
                session.setAttribute("customer", newCustomer);
            }
            else
            {
                request.setAttribute("passwords_dont_match", "Passwords Don\'t Match!!");
                return null;
            }
        }

        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            String entered_userid = request.getParameter("entered_userid");
            String entered_password = request.getParameter("entered_password");
            customer = Customer.getUser(entered_userid, entered_password, connectionPool);
            // Remember that the user is now logged in.
            session.setAttribute("customer", customer);
        }
        
        return customer;
    }
    
    private synchronized int getNextOrderPrimaryKey()
    {
        if (order_next_primary_key < 0)
            order_next_primary_key= (int)(System.currentTimeMillis()/1000);
        else
            order_next_primary_key +=1;
        return order_next_primary_key;
    }
    
    private void getCommittedOrders(
            HttpSession session, Customer customer, 
            ConnectionPool connectionPool) throws SQLException
    {
        ArrayList<Order> orders =Order.query_order(customer.getUserid(),  connectionPool);
        session.setAttribute("orders", orders);
    }
    
    private void  getAndProcessShoppingCart(
            HttpSession session, HttpServletRequest request, 
            Customer customer, ConnectionPool connectionPool) throws SQLException
    {
                ArrayList<ShoppingCartItem> shoppingCart = 
                        (ArrayList<ShoppingCartItem>)session.getAttribute("shoppingCart");
                if (shoppingCart == null)
                {
                    shoppingCart = new ArrayList<ShoppingCartItem>();
                    session.setAttribute("shoppingCart", new ArrayList<ShoppingCartItem>());
                }
                if ("Process Order".equals(request.getParameter("action")))
                {
                    int order_pk = getNextOrderPrimaryKey();
                    String date = new Date().toString();
                    String userid_fk = customer.getUserid();
                    Order order = new Order(order_pk, userid_fk, date);
                    order.insert(connectionPool);
                    
                    for (int i=0; i < shoppingCart.size(); i++)
                    {
                        ShoppingCartItem item = shoppingCart.get(i);
                        int quantity = item.getQuantity();
                        String web_page_item_pk = item.getWeb_page_item_pk();
                        Order_item order_item = new   Order_item(order_pk,  quantity,  web_page_item_pk);
                        order_item.insert(connectionPool);
                    }
                    // We have just processed all of the shopping cart entries, so we need to empty it
                    shoppingCart.clear();
                }
                int totalPrice =0;
                for (int i=0; i < shoppingCart.size(); i++)
                {
                    ShoppingCartItem item = shoppingCart.get(i);
                    int quantity = item.getQuantity();
                    Web_item web_item = Web_item.getWeb_item(item.getWeb_page_item_pk(), connectionPool);
                    int price = web_item.getPriceValue();
                    totalPrice += price * quantity;
                    item.setWeb_item(web_item);
                }
                session.setAttribute("totalPrice", Util.dollarsCents(totalPrice));
                int tax = (int)(totalPrice * .06);
                session.setAttribute("tax", Util.dollarsCents(tax));
                int shipping = 500; // flat charge
                session.setAttribute("shipping", Util.dollarsCents(shipping));
                session.setAttribute("grandTotal", Util.dollarsCents(totalPrice + tax + shipping));
                
        
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
