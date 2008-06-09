<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : Administrador
    Created on : 07/06/2008, 23:51:08
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
                    <webuijsf:form id="form1" virtualFormsConfig="">
                        <div style="height: 72px; left: 0px; top: 0px; position: absolute; width: 288px">
                            <jsp:directive.include file="top.jspf"/>
                        </div>
                        <webuijsf:textField id="textField1" label="Nome" style="left: 258px; top: 194px; position: absolute"/>
                        <webuijsf:textField id="textField3" label="Matrícula" style="left: 240px; top: 168px; position: absolute"/>
                        <webuijsf:textField id="textField2" label="Email" style="left: 261px; top: 221px; position: absolute"/>
                        <webuijsf:button id="button1" style="left: 368px; top: 277px; position: absolute" text="cadastrar"/>
                        <webuijsf:passwordField id="passwordField1" label="Senha" style="left: 257px; top: 246px; position: absolute"/>
                        <webuijsf:message id="msg" showDetail="false" showSummary="true" style="left: 216px; top: 144px; position: absolute"/>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
    <webuijsf:form binding="#{view$web$Cadastrar.form1}" id="form1"/>
</jsp:root>
