<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : Home
    Created on : 08/06/2008, 00:19:54
    Author     : Giuseppe
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <webuijsf:page id="page1">
            <webuijsf:html id="html1">
                <webuijsf:head id="head1">
                    <webuijsf:link id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body id="body1" style="-rave-layout: grid">
                    <webuijsf:form id="form1">
                        <div style="left: 0px; top: 0px; position: absolute">
                            <jsp:directive.include file="top.jspf"/>
                        </div>
                        <webuijsf:passwordField id="passwordField1" label="Senha" style="left: 340px; top: 190px; position: absolute"/>
                        <webuijsf:textField id="textField1" label="Login" style="left: 343px; top: 165px; position: absolute" valueChangeListenerExpression="#{view$web$Home.textField1_processValueChange}"/>
                        <webuijsf:button id="button1" style="left: 476px; top: 217px; position: absolute" text="logar"/>
                        <webuijsf:hyperlink id="hyperlink1" style="left: 358px; top: 237px; position: absolute" text="Ainda não está cadastrado?" url="/faces/view/web/Cadastrar.jsp"/>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
