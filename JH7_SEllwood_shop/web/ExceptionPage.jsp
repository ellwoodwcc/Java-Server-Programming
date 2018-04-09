

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exception Page Page</title>
    </head>
    <body>
        <h1>Exception Occurred</h1>
        Try reloading the page
        <p>Information on the Exception:
            <br/>
            <%
                Exception ex = (Exception)request.getAttribute("exception");
                out.println(ex);
                StackTraceElement[] stack =	ex.getStackTrace();
                for (int i=0; i < stack.length; i++)
                    out.println("<br/>"+stack[i]);
            %>
        </p>
    </body>
</html>
