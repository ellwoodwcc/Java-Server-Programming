<!-- 
    Document   : JSPOneliner
    Created on : Oct 2, 2015, 11:32:34 AM
    Author     : Steve-->

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@page import= "JSPOneLiner.* "%>
<%
//    ServletConfig scfg = getServletConfig();
//    ServletContext sctx = getServletContext();
//    OneLinerUtils2 quote = new OneLinerUtils2(sctx,scfg);
 //   OneLinerUtils2 quote; 
%>  
<%!
OneLinerUtils2 quote; 
    public void jspInit() {
        //OneLinerUtils2 quote;
        ServletConfig scfg = getServletConfig();
        ServletContext sctx = getServletContext();
        String oneLinerFilename = sctx.getInitParameter("oneLinerFilename");

        String fullFilePath = sctx.getRealPath("/WEB-INF/" + oneLinerFilename);
        fullFilePath = OneLinerUtils2.init(getServletConfig(), getServletContext());
        System.out.println("fullFilePath=" + fullFilePath);
        quote = new OneLinerUtils2(getServletContext(), getServletConfig());
        
    }
//oneLinerhere = new OneLinerUtils2(sctx,sctx);%>

<%
//    ServletConfig scfg = getServletConfig();
//    ServletContext sctx = getServletContext();
//    OneLinerUtils2 quote = new OneLinerUtils2(sctx,scfg);
 //   OneLinerUtils2 quote = new OneLinerUtils2(getServletContext(), getServletConfig());
    
%>  
<!--public void jspInit()
  {  n  
        fullFilePath = GuestBookUtils.init(getServletConfig(), getServletContext());
  } these are auto created when create servelet or jsp
    .... do what you need to do-->
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ELLWOOD JSP JH3</title>
    </head>
    <body bgcolor="#FFFF99">
        <h3> Quote of the Day </h3>
        <pre>
            <%= quote.getNext()%>
        <pre>
        <form action="JSPOneliner.jsp">
        <input type="submit" value="Next" >
        </form>
    </body>
</html>
