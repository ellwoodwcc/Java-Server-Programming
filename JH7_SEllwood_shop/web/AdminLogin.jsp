<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin Login to Candidate Shop</title>
    </head>
    <body bgcolor="#F0E68C">
        <h1>Admin Login to Candidate Shop</h1>
        <h1>for this demo only use ad and ad</h1>
        <form method="post" action="AdminServlet">
            Enter userid: <input type="text" name="entered_userid">
            <br/>Enter password: <input type="password" name="entered_password">
            <br/><input type="submit" value="login">
        </form>
        
    </body>
</html>

