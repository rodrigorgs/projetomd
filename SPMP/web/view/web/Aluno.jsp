<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : Aluno
    Created on : 08/06/2008, 00:23:51
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
                        <webuijsf:tabSet id="tabSet1" selected="tab1" style="left: 1px; top: 128px; position: absolute">
                            <webuijsf:tab actionExpression="#{view$web$Aluno.tab1_action}" id="tab1" text="Pré-matrícula">
                                <webuijsf:panelLayout binding="#{view$web$Aluno.layoutPanel1}" id="layoutPanel1" style="height: 299px; position: relative; width: 814px; -rave-layout: grid"/>
                            </webuijsf:tab>
                            <webuijsf:tab id="tab2" text="Matrícula">
                                <webuijsf:panelLayout id="layoutPanel2" style="height: 273px; position: relative; width: 813px; -rave-layout: grid"/>
                            </webuijsf:tab>
                            <webuijsf:tab id="tab3" text="Histórico">
                                <webuijsf:panelLayout id="layoutPanel3" style="height: 249px; position: relative; width: 813px; -rave-layout: grid"/>
                            </webuijsf:tab>
                        </webuijsf:tabSet>
                    </webuijsf:form>
                </webuijsf:body>
            </webuijsf:html>
        </webuijsf:page>
    </f:view>
</jsp:root>
