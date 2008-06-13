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
        <jsp:include page="topo.jsp" flush="true" />
        <div id="titulo">Seja bem-vindo!</div>        
        <form action="Login.do" method="POST">
            <table align="center">
                <c:if test="${msg != null}">
                <tr>
                    <td id="erro" colspan="2">${msg}</td>
                </tr>
                </c:if>                
                <tr>
                    <td>Login</td>
                    <td><input type="text" name="login" value="${login}" /></td>
                </tr>
                <tr>
                    <td>Senha</td>
                    <td><input type="password" name="senha" value="" /></td>
                </tr>
                <tr>
                    <td colspan="2" align="right"><a href="CadastroAluno.jsp">Ainda não é cadastrado?</a></td>
                </tr>
                <tr>
                    <td colspan="2" align="right"><input type="submit" value="login" /></td>
                </tr>
                
            </table>
             
        </form>
    </body>
</html>
