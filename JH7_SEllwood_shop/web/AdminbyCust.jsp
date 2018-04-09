<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Checkout by Customer</title>
    </head>
    <body bgcolor="#F0E68C">
        <br/><a href="AdminServlet?action=logoff" >Logoff </a>
        
        <h1>Orders by Customer</h1>
        
        
        <c:forEach var="customer" items="${customers}"   >
            <h2>Customer(userid): ${customer.userid}</h2>
        
            <br/>First Name: ${customer.first_name} 
            <br/>Last Name: ${customer.last_name}
            <br/>Address: ${customer.address}
             
            <h3>Orders</h3>
                <c:forEach var="order" items="${customer.orders}"   >
                    <br/>Order date: ${order.date}
                         
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
                                    <input type="hidden" name="orderno"  value="${order.order_pk}">
                                    <input type="hidden" name="orderdate"  value="${order.date}">
                                    <input type="hidden" name="itemname"  value="${item.web_item.title}">
                                    <input type="hidden" name="titlepk"  value="${item.web_item.web_page_item_pk}">
                                    <input type="hidden" name="customerid" value="${customer.userid}" >
                                    <input type="hidden" name="howmany" value="${item.quantity}" >
                                    <input type="hidden" name="fname"  value="${customer.first_name}">
                                    <input type="hidden" name="lname" value="${customer.last_name}" >
                                    <input type="hidden" name="addr" value="${customer.address}" >
                                    <input type="hidden" name="url" value="${item.web_item.imageUrl}" >
                                    <br/><input type="submit" name="action" value="Fill_by_this_Item"/>
                                 </form>     

                             </c:forEach>
                       </ul>
                    
<!--                     <br/><input type="submit" name="action" value="Fill by Order Items"/>
                          <input type="hidden" name="adminbyorders" value="${adminbyorders}" >-->
                </c:forEach>
                </ol>
            <hr/>
        </c:forEach>
             
        
        
        
    </body>
</html>



