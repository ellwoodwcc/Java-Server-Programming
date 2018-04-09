
package servlets;

import candidate_database.ShoppingCartItem;
import candidate_database.ConnectionPool;
import candidate_database.WebPage;
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


public class WebPageServlet extends HttpServlet {

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
            //Does anything actually call this in order for it to occur?
            ArrayList<ShoppingCartItem> shoppingCart = (ArrayList<ShoppingCartItem>)session.getAttribute("shoppingCart");//checks session to see if arryalist shoppingcare is alive 
            if (shoppingCart == null)//if cart arryl doesn't exist then build a new shopping cart
            {
                shoppingCart = new ArrayList<ShoppingCartItem>();//?W This Shopingcar is defind in the shoppingcaritem.java           
                session.setAttribute("shoppingCart", shoppingCart); //writes it down into the session not in database like notepad
            }
            //if orderedItem, then check contains(Clinton)           
            String web_page_desired = request.getParameter("web_page_desired");//this is called twice on WebPageDisplay wpd.seewebpage.java
            if (web_page_desired == null)
                web_page_desired ="_"; // unspecified
            WebPage webPage = WebPage.getWebPage(web_page_desired, connectionPool);
//this calls webpage.java where web-pagedesired="select * from "+ table_name +" where web_page_pk="+Util.wrapString(web_page_desired) ;
                
            String pageTitle ="";
            if (webPage != null)//if have picked a webpage
                pageTitle = "Presidential Paraphernalia: " +webPage.getTitle();
            else
                pageTitle = "Presidential Paraphernalia: Choose a President page and click \"go to new page\"";
            
            // Get the list of other web pages for the user
            ArrayList<WebPage> otherPages = WebPage.queryOthers(web_page_desired,  connectionPool);
            ArrayList<Web_item> webPage_items; 
            webPage_items = Web_item.query(web_page_desired, connectionPool); 
 //this calls query in web_item.java where web-pagedesired=ArrayList<Web_item> query(String web_page, ConnectionPool conn       
            // Update the shopping cart as necessary
            String action = request.getParameter("action");//this is getting it from the jsp?Then why do we run this bnefore even go to shop? these are null first run throg
            String quantityStr = request.getParameter("quantity");
            String web_page_item_pk = request.getParameter("web_page_item_pk");
            if ("Add to Cart".equals(action))
            {
                ShoppingCartItem.add(quantityStr, web_page_item_pk , shoppingCart);
            }
            else if ("Delete".equals(action))
            {
                ShoppingCartItem.delete( web_page_item_pk , shoppingCart);
            }
            else if ("Update".equals(action))
            {
                ShoppingCartItem.update(quantityStr, web_page_item_pk , shoppingCart);                
            }
            
            // Mark all of the Web_items that are also in the shopping cart.
            // This will show the user what he has already picked and how many.
            for (int wp=0; wp < webPage_items.size(); wp++)//wpitems is the array list we defined above
            {
                Web_item web_item = webPage_items.get(wp);
                for (int crt =0; crt < shoppingCart.size(); crt++)
                {
                    ShoppingCartItem item = shoppingCart.get(crt);
                    if (item.matches(web_item.getWeb_page_item_pk()))
                    {
                        web_item.setQuantity(item.getQuantity());
                        break;
                    }
                }
            }

            request.setAttribute("web_page_desired", web_page_desired);//opposite of getparameter
            request.setAttribute("pageTitle", pageTitle);
            request.setAttribute("otherPages", otherPages);
            request.setAttribute("webPage_items", webPage_items);//goes here wheh press enter clemshop
  //what is going on with setAttribute   also what defines the query?        
            String url = "/WebPageDisplay.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            System.out.println("WePageServlet - SQLException caught: " + e);
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
