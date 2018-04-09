
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Checkout</title>
    </head>
    <body bgcolor="#E0FFFF">
        <a href="WebPageServlet" > Return to Shopping </a>
        <br/><a href="ViewShoppingCartServlet" > View and Update Shopping Cart </a>
        <br/><a href="CheckoutServlet?action=logoff" >Logoff </a>
        
        <h1>Checkout for Userid: ${customer.userid}</h1>
        
        <br/>First Name: ${customer.first_name}
        <br/>Last Name: ${customer.last_name}
        <br/>Address: ${customer.address}
        <hr/> 
        <c:choose>
            <c:when test="${shoppingCart.size()==0}">
                <h3>Shopping Cart is Empty</h3>
            </c:when> 
            <c:when test="${shoppingCart.size() > 0}">
                Price of Goods: ${totalPrice}
                <br/>Tax: ${tax}
                <br/>Shipping: ${shipping}
                <br/>Grand Total: ${grandTotal}
                <p>
                <form action="CheckoutServlet">
                    <input type="submit" name="action" value="Process Order">
                </form>
                </p>

                <hr/>  
                <c:forEach var="item" items="${shoppingCart}"   >
                       price: ${item.web_item.price}
                       <form action="ViewShoppingCartServlet">
                           <input type="hidden" name="web_page_item_pk"  value="${item.web_page_item_pk}">
                           quantity: <input type="text" name="quantity" value="${item.quantity}" size="8">

                       </form>
                       <p>${item.web_item.title} </p> 
                       <img src="${item.web_item.imageUrl}" />                           
                   <hr/>
                </c:forEach>
           </c:when> 
        </c:choose>
                   
       <hr/>
       <h3>Committed Orders  for Userid: ${customer.userid}</h3>
       <ol>
        <c:forEach var="order" items="${orders}"   >
            <li>
                orderid: ${order.order_pk}
                <br/>date: ${order.date}
                <ul>
                    <c:forEach var="item" items="${order.order_items}"   >
                        <li>
                           price: ${item.web_item.price}
                           <br/>quantity: ${item.quantity}
                           <br/>${item.web_item.title}
                           <br/><img src="${item.web_item.imageUrl}" height="75" /> 
                        </li>

                    </c:forEach>
            </ul>
            </li>
        </c:forEach>
       </ol>
             
        
        
        
    </body>
</html>


