<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Checkout by Order Items</title>
        
    </head>
    <body bgcolor="#F0E68C">
        <br/><a href="AdminServlet?action=logoff" >Logoff </a>
        <h2>Administration: Delivery Order for Selected Item:</h2>
       
        <form action="AdminServlet">
            
            You chose &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${title1}<br> 
            Order No. <input type="text"  name="orderitem" value="${orderitemB}"><br>  
            Quantity  <input type="text"  name="howmany" value="${quantity1}"><br>  
            UserID     <input type="text"  name="customerid" value="${useridC}"><br>
            Order Date<input type="text"  name="orderdate" value="${orderdate}"><br>
            Name      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; "${fname} ${lname}"> 
            <input type="text"  name="lname" size="13" value="${lname}"><br> 
            Address   <input type="text"  name="addr" value="${addr}"><br> 
        </form>
        </table>
   </body>
</html>