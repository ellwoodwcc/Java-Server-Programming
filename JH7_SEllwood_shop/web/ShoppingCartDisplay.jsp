
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Your Shopping Cart</title>
    </head>
    <body bgcolor="#E0FFFF">
        <a href="WebPageServlet" > Return to Shopping </a>
        <br/><a href="CheckoutServlet" > Go to Checkout </a>
        
        <h1>Contents of your Shopping Cart</h1>
       
         
           
        <c:forEach var="item" items="${shoppingCart}"   >
               price: ${item.web_item.price}
               <form action="ViewShoppingCartServlet">
                   <input type="hidden" name="web_page_item_pk"  value="${item.web_page_item_pk}">
                   quantity: <input type="text" name="quantity" value="${item.quantity}" size="8">
                   <br/>
                        <input type="submit" name="action" value="delete">
                        <input type="submit" name="action" value="update">
               </form>
               <p>${item.web_item.title} </p> 
               <img src="${item.web_item.imageUrl}" />                           
           <hr/>
        </c:forEach>
             
        
        
        
    </body>
</html>

