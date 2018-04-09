<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Checkout by Customer</title>
    </head>
    <body bgcolor="#F0E68A">
        <br/><a href="AdminServlet?action=logoff" >Logoff </a>
        
        <h1>Orders by Items with customer addresses</h1>
        <h2>For Item: ${itemname}</h2>
        <br/><img src="${url}" height="125" />   
        
        
        <c:forEach var="customer" items="${customers}"   >
            <h3>Customer</h3>
        
            Name: ${customer.first_name} ${customer.last_name}<br>
            <br/>Address: ${customer.address}<br>
                <br/>Quantity: ${quantity1}
             
           
                <c:forEach var="order" items="${customer.orders}"   >
                    <br/>Order date: ${order.date}
                         
                                          
<!--                     <br/><input type="submit" name="action" value="Fill by Order Items"/>
                          <input type="hidden" name="adminbyorders" value="${adminbyorders}" >-->
                </c:forEach>
                <!--</ol>-->
            <hr/>
        </c:forEach>
             
        
        
        
    </body>
</html>



