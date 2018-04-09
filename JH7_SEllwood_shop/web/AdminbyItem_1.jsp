<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Checkout by Customer</title>
    </head>
    <body>
        <br/><a href="AdminServlet?action=logoff" >Logoff </a>
        
        <h1>Customers for Orders</h1>
        
        <c:forEach var="item" items="${order.order_items}"   >
                                 <li>
                                    price: ${item.web_item.price}
                                    <br/>quantity: ${item.quantity}
                                    <br/>${item.web_item.title}
                                    <br/><img src="${item.web_item.imageUrl}" height="125" /> 
<!--                                    <input type="hidden" name="forwardsuborder_desired" value="${forwardsuborder_desired}" >-->
                                    
                                 </li>
        <c:forEach var="customer" items="${customers}"   >
            <h2>Customer(userid): ${customer.userid}</h2>
        
            <br/>First Name: ${customer.first_name}
            <br/>Last Name: ${customer.last_name}
            <br/>Address: ${customer.address}
             
            <h3>Orders</h3>
                <ol>
                
                 <c:forEach var="order" items="${customer.orders}"   >
                     <li>
                         orderid: ${order.order_pk}
                         <br/>date: ${order.date}
                         
                         <ul>
                             <c:forEach var="item" items="${order.order_items}"   >
                                 <li>
                                    price: ${item.web_item.price}
                                    <br/>quantity: ${item.quantity}
                                    <br/>${item.web_item.title}
                                    <br/><img src="${item.web_item.imageUrl}" height="125" /> 
<!--                                    <input type="hidden" name="forwardsuborder_desired" value="${forwardsuborder_desired}" >-->
                                    
                                 </li>
                                
                                 <form action="AdminServlet">
                                    <input type="hidden" name="orderitem"  value="${order_item.order_item_pk}">
                                    <br/><input type="submit" name="action" value="Fill_by_this_Item"/>
                                 </form>     

                             </c:forEach>
                     </ul>
                     </li>
<!--                     <br/><input type="submit" name="action" value="Fill by Order Items"/>
                          <input type="hidden" name="adminbyorders" value="${adminbyorders}" >-->
                 </c:forEach>
                </ol>
            <hr/>
        </c:forEach>
             
        
        
        
    </body>
</html>