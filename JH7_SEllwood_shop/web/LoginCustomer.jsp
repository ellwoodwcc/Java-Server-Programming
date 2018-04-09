<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login to Clem Shop</title>
    </head>
    <body bgcolor="#FF0000">
        <a href="WebPageServlet" > Return to Shopping </a>
        <h1>Login</h1>
        <form method="post" action="CheckoutServlet">
            Enter userid: <input type="text" name="entered_userid">
            <br/>Enter password: <input type="password" name="entered_password">
            <br/><input type="submit" value="login">
        </form>
        
        <hr/>
        <h1>Create New Customer Account</h1>
        <h3>${passwords_dont_match}</h3>
        <form method="post" action="CheckoutServlet">
            Enter userid: <input type="text" name="userid">
            <br/>Enter First Name: <input type="text" name="first_name">
            <br/>Enter Last Name: <input type="text" name="last_name">
            <br/>Enter password: <input type="password" name="password">
            <br/>Enter password a second time: <input type="password" name="second_password">
            <br/>Address: <input type="text" name="address">
            <br/><input type="submit" name="action" value="Create New Account">
        </form>
             
        
        
        
    </body>
</html>
