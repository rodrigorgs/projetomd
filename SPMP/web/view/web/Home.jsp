<?xml version="1.0" encoding="ISO-8859-1"?>
<!-- 
    Document   : Page1
    Created on : Jun 6, 2008, 5:47:19 PM
    Author     : Stefani Pires
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="ISO-8859-1"/>
    <f:view>
        <webuijsf:page binding="#{view$web$Home.page1}" id="page1">
            <webuijsf:html binding="#{view$web$Home.html1}" id="html1">
                <webuijsf:head binding="#{view$web$Home.head1}" id="head1">
                    <webuijsf:link binding="#{view$web$Home.link1}" id="link1" url="/resources/stylesheet.css"/>
                </webuijsf:head>
                <webuijsf:body binding="#{view$web$Home.body1}" id="body1" style="-rave-layout: grid">
                    <webuijsf:form binding="#{view$web$Home.form1}" id="form1"/>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
