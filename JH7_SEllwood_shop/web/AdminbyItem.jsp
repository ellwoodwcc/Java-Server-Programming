<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Checkout by Order Items</title>
    </head>
    <body bgcolor="#F0E68A">
        <br/><a href="AdminServlet?action=logoff" >Logoff </a>
        
        <h1>Orders by Items with customer addresses</h1>
        <h1>For Item: ${itemname}</h1>
        <br/><img src="${url}" height="125" />   
                    
                     
                       <c:forEach var="customer" items="${customers}"   >
                          
                            <br/>Customer: ${customer.first_name} ${customer.last_name}
                            <br/>Address: ${customer.address}
                              <br><br><br>      
                                               
                               <c:forEach var="orders1" items="${orders1}"   >
                                 <br/>Order Date: ${order.date}
                                    <br>
                            
                               </c:forEach>      
                      </c:forEach>   
            
               
                         
            <hr/>

  
   </body>
</html>