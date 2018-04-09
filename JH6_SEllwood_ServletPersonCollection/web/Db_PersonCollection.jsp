<%-- 
    Document   : DB_PersonCollection
    Created on : Nov 13, 2015, 9:58:47 AM
    Author     : Stephen
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Person Collection</title>
    </head>
    <body>
        <h1>Person Collection DB2</h1>
        <form action="DB_PersonServlet">
            <input type="submit" name="action" value="Clear List"/>
        </form>
        <p>
<!-- if these request objects have different names from getters, the form will still launch and take  inserts, but it can't update-->
        <form action="DB_PersonServlet">
            <input type="text" name="firstname" /> First Name
            <br><input type="text" name="lastname" /> Last Name
            <br><input type="text" name="eyecolor" /> Eye Color
            <br><input type="text" name="haircolor" /> Hair Color
            <br><input type="text" name="xyheight" /> Height
            <br><input type="text" name="zweight" /> Weight (digits only!)
            <br><input type="submit" name="action" value="add" />
        </form>
        <hr>                       
        <h3>${errorMessageA}</h3>
        
        <table border="3">
            <tr><th>First Name</th><th>Last Name</th><th>Eye Color</th><th>Hair Color</th><th>Height</th><th>Weight</th><th></th></tr>
<!-- //this tells it to go find PersonCollection. first stop is??? arraylist sessiongetattribute? Or are we atalkingabout the sql db?  
the tag Personcollectiona will work whether its in the session object or request object. it will search through the containers unti it gets matching name  -->
            <c:forEach var="person" items="${PersonCollectionA}"  varStatus="loopStatus" >
            <tr>
            <form action="DB_PersonServlet">
                <td><input type="text"  name="firstname" value="${person.fname}" /></td>
                <td><input type="text"  name="lastname" value="${person.lname}" /></td>
                <td><input type="text"  name="eyecolor" value="${person.eyecolor}" /></td>
<!--these values must match ones at top for update to work add will work thoguh              -->
                <td><input type="text"  name="haircolor" value="${person.hcolor}" /></td>
                <td><input type="text"  name="xyheight" value="${person.height}" /></td>
                <td><input type="text"  name="zweight" value="${person.weight}" /></td>
                <td><input type="submit" name="action" value="remove" />
                     <input type="submit" name="action" value="update" />
                     <input type="hidden" name="index" value="${person.index}" />
                </td>
            </form>
            </tr>
         </c:forEach>
        </table>
    </body>
</html>
