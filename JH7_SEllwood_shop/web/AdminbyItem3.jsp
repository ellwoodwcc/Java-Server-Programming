<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Checkout by Order Items</title>
    </head>
    <body>
        <br/><a href="AdminServlet?action=logoff" >Logoff </a>
        

        <h1>Administration: Orders for Item: "${orderitemB}"</h1>
        <br/>quantity: ${quantity1}
        <h2>Order name: ${title1}
        <br/> Price: ${item.web_item.price}/broken
        <br/><img src="${item.web_item.imageUrl}" height="125" /> 
<!--            <ol>-->
            <c:forEach var="order" items="${customer.orders}"   >
<!--                <li>-->
                orderid: ${order.order_pk}
                <br/>date: ${order.date}
                <h2>Customers who ordered this item</h2>-->
                
<!--                <form action="AdminServlet">-->
                 <c:forEach var="customer" items="${customers}"   >
                     <li>
                         First Name: ${customer.first_name}
                         <br/>Last Name: ${customer.last_name}
                         <br/>Address: ${customer.address}
                         <br/>Order date: ${order.date}
                         <br/>quantity: ${item.quantity}
                     </li>
                 </c:forEach>
<!--                </form>     -->
                
<!--            <br/><input type="submit" name="action" value="Invoice Page"/>
            <input type="hidden" name="oneorder" value="${oneorder}" >-->
            </c:forEach>
            <!--</ol>-->    
<!--        <hr/>-->
        
        
    
   </body>
</html>