<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="spmp.dao.data.PrologData" %>
<%@ page import="spmp.business.prolog.Prolog" %>
<%@ page import="spmp.business.SPMPFacade" %>
<%@ page import="spmp.bean.Disciplina" %>
<%@ page import="jpl.*" %>
<%@ page import="java.util.*" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>Hello World!</h2>
            <form>
                <%
                    SPMPFacade facade = SPMPFacade.getInstance();
                    HashMap<java.lang.Integer, List<Disciplina>> map = facade.getTodasDisciplinasPorSemestre();
                    
                    List<Disciplina> discs = map.get(0);
                    
                    for (Disciplina d : discs) {
                %>
                            <ul>
                <li>
                    <input type="checkbox" id="<%= d.getIdDisciplina() %>"/>
                    <%= d.getCodDisciplina() %> - <%= d.getNome() %></li>
            </ul>
                
                <%
                }
                    %>

            <input type="submit"/>
        </form>
    </body>
</html>
