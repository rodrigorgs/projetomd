<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SPMP - Sistema de Planejamento de Matrículas com Prolog</title>
        <link rel="stylesheet" href="./spmp.css" type="text/css"/>
    </head>
    <body>
        <c:import url="topo.jsp" />
        <c:import url="menu.jsp" />
        <c:import url="${requestScope.step}" />
    </body>
</html>
