<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

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
        <h2>Escolha as disciplinas</h2>
        <form>
            <%
            try {
                SPMPFacade facade = SPMPFacade.getInstance(getServletContext().getRealPath("/regras.pl"));
                HashMap<java.lang.Integer, List<Disciplina>> map = facade.getTodasDisciplinasPorSemestre();

                List<Disciplina> discs = map.get(0);

                for (Disciplina d : discs) {
            %>
            <ul>
                <li>
                    <input type="checkbox" id="<%= d.getIdDisciplina()%>"/>
                    <%= d.getCodDisciplina()%> - <%= d.getNome()%>
                    <% 
                    List<Disciplina> prerequisitos = facade.getPrerequisitos(d);
                    if (!prerequisitos.isEmpty()) {
                    %>
                    <small>(depende de<%
                        for (Disciplina pre : prerequisitos) {
                        %> &nbsp;<abbr title="<%= pre.getNome()%>"><%= pre.getCodDisciplina()%></abbr><%
                        }
                    %>)</small>
                    <% }%>
                </li>
            </ul>
            
            <%
                }
            } catch (Throwable e) {
                out.println(e.getMessage() + "<br/>");
                StackTraceElement[] ste = e.getStackTrace();
                for (StackTraceElement elem : ste) {
                    out.println(elem.toString() + "<br/>");
                }
            }
            %>
            
            <input type="submit"/>
        </form>
    </body>
</html>
