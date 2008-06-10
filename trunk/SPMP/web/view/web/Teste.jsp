<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="spmp.dao.data.PrologData" %>
<%@ page import="spmp.business.prolog.Prolog" %>
<%@ page import="jpl.*" %>
<%@ page import="java.util.Hashtable" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Hello World!</h2>
            <form>
            <%
            try {
            PrologData data = new PrologData();
            data.load();
            Hashtable[] results = Query.allSolutions(Prolog.comp("disciplina", "Id", "Cod", "Nome"));
            //String[] discs = Prolog.getVar("Cod", res);
            for (Hashtable disc : results) {
            %>
            <ul>
                <li>
                    <input type="checkbox" id="<%= disc.get("Id") %>"/>
                    <%= disc.get("Cod") %> - <%= disc.get("Nome") %></li>
            </ul>
            <% } 
            } catch (Throwable e) {
                out.println("Erro ");
                out.println(e.getMessage() + "<br/>");
                StackTraceElement[] ste = e.getStackTrace();
                for (StackTraceElement elem : ste) {
                    out.println("<li>" + elem.toString() + "</li>");
                }                    
            }
            %>
            <input type="submit"/>
        </form>
    </body>
</html>
