<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import="persons.*"  %>
<%
    PersonCollection personCollection = PersonCollection.update(pageContext);
 %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Person Collection</title>
    </head>
    <body bgcolor="#FF99">
        <h1>Person Collection</h1>
        <form action="personCollection.jsp"> 
            <input type="submit" name="action" value="Clear List"/>
        </form>
        <p>
        <form action="personCollection.jsp">
            <input type="text" name="fname" /> First Name
            <br><input type="text" name="lname" /> Last Name
            <br><input type="text" name="eyecolor" /> Eye Color
            <br><input type="text" name="haircolor" /> Hair Color
            <br><input type="text" name="height" /> Height
            
            <br><input type="text" name="weight" /> Weight (pounds in decimal notation)
            <br><input type="submit" name="action" value="add" />
        </form>
        <hr>
        <h3><%=personCollection.getErrorMessage()%></h3>
        
        <table border="3">
        <tr><th>First Name</th><th>Last Name</th><th>Eye Color</th><th>Hair Color</th><th>Height/th><th>Weight</th><th></th></tr> 
        <%                              //ththfor empty button
            for (int i=0; i <  personCollection.size(); i++)
            {
                Person person = personCollection.getPerson(i);
         %>
         <tr>
         
            <form action="personCollection.jsp">
                <td><input type="text"  name="fname" value="<%=person.getFName()%>" /></td>
                <td><input type="text"  name="lname" value="<%=person.getLName()%>" /></td>
                <td><input type="text"  name="eyecolor" value="<%=person.getEColor()%>" /></td>
              
                <td><input type="text"  name="haircolor" value="<%=person.getHColor()%>" /></td>
                <td><input type="text"  name="height" value="<%=person.getHeight()%>" /></td>
                <td><input type="text"  name="weight" value="<%=person.getWeight()%>" /></td>
                <td><input type="submit" name="action" value="remove" />
                     <input type="submit" name="action" value="update" />
                     <input type="hidden" name="index" value="<%= i %>" />
                </td>
            </form>
        </tr>
         <%
            }
          %>
        </table>
    </body>
</html>


