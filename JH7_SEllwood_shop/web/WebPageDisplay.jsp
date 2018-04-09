
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Presidential Online Shop</title>
    </head>
    <body bgcolor="#D8BFD8">
        Pick Shopping Page: 
        <form action="WebPageServlet">
            <select name="web_page_desired">
                <c:forEach var="webPage" items="${otherPages}"   >
                    <option value="${webPage.web_page_pk}">${webPage.title}        
                </c:forEach>
            </select>
            <input type="submit" value="go to new page">
        </form>
        
        <a href="ViewShoppingCartServlet">View Shopping Cart</a>       
        <br/><a href="CheckoutServlet" > Go to Checkout </a>
        <br/><a href="AdminServlet" > Go to Administrative Pages Great new features! (demo only) </a>
        
        <h1>${pageTitle}</h1>
       
<!--//It runs through this before even loading page        -->
<!--Choose a Presidential page and click "go to new page"           -->
        <c:forEach var="item" items="${webPage_items}"   >
               <p>${item.title} </p> 
               <img src="${item.imageUrl}" />
               price: ${item.price}
               <form action="WebPageServlet">
                   <input type="hidden" name="web_page_item_pk"  value="${item.web_page_item_pk}">
                   <input type="hidden" name="web_page_desired" value="${web_page_desired}" >
                   <input type="hidden" name="itemOrdered" value="${itemOrdered}" >
<!--                   how make the initial value to be 1?-->
                   quantity: <input type="number" name="quantity" value="${item.quantity}" size="8">
<!--              how make number not be negative?can call absolute value?-->
                   <br/>
                   <c:choose>
                       <c:when test="${item.quantity==1}">
                           <input type="submit" name="action" value="Add to Cart">
                       </c:when>                          
                       <c:when test="${item.quantity > 1}">
                            <input type="submit" name="action" value="Delete">
                            <input type="submit" name="action" value="Update">
                       </c:when>
                   </c:choose>
               </form>
                                          
           <hr/>
        </c:forEach>
                   
    </body>
</html>
