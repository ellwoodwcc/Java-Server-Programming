
package servlets;

import candidate_database.ShoppingCartItem;
import candidate_database.ConnectionPool;
import candidate_database.Util;
import candidate_database.Web_item;
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

public class ViewShoppingCartServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            ServletContext servletContext = getServletContext();
            ConnectionPool connectionPool = (ConnectionPool) servletContext.getAttribute("connectionPool");
            HttpSession session = request.getSession(true);
            ArrayList<ShoppingCartItem> shoppingCart = (ArrayList<ShoppingCartItem>)session.getAttribute("shoppingCart");
            if (shoppingCart == null)
            {
                shoppingCart = new ArrayList<ShoppingCartItem>();
                session.setAttribute("shoppingCart", shoppingCart);               
            }
            
            for (int i=0; i < shoppingCart.size(); i++)
            {
                ShoppingCartItem item = shoppingCart.get(i);
                Web_item web_item = Web_item.getWeb_item(item.getWeb_page_item_pk(), connectionPool);
                item.setWeb_item(web_item);
            }
                       
            String action = request.getParameter("action");
            String quantityStr = request.getParameter("quantity");
            String web_page_item_pk = request.getParameter("web_page_item_pk");
            if ("add".equals(action))
            {
                ShoppingCartItem.add(quantityStr, web_page_item_pk , shoppingCart);
            }
            else if ("delete".equals(action))
            {
                ShoppingCartItem.delete(web_page_item_pk , shoppingCart);
            }
            else if ("update".equals(action))
            {
                ShoppingCartItem.update(quantityStr, web_page_item_pk , shoppingCart);                
            }
//            int totalPrice =0;//added
//                for (int i=0; i < shoppingCart.size(); i++)
//                {
//                    ShoppingCartItem item = shoppingCart.get(i);
//                    int quantity = item.getQuantity();
//                    Web_item web_item = Web_item.getWeb_item(item.getWeb_page_item_pk(), connectionPool);
//                    int price = web_item.getPriceValue();
//                    totalPrice += price * quantity;
//                    item.setWeb_item(web_item);
//                }
//                session.setAttribute("totalPrice", Util.dollarsCents(totalPrice));
//                int tax = (int)(totalPrice * .06);
//                session.setAttribute("tax", Util.dollarsCents(tax));     
            String url = "/ShoppingCartDisplay.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            System.out.println("ViewShoppingCartServlet- SQLException caught: " + e);
            e.printStackTrace(System.out);
            
            request.setAttribute("exception", e);
            String url = "/ExceptionPage.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
            dispatcher.forward(request, response);
        } finally {
            out.close();
        }
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
