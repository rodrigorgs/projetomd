<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    function doCLick(){
        document.getElementById('prematricula').submit();
    }
    function doPostEfetivo(){
        form = document.getElementById('matricula');
        input = document.createElement("input");
        input['type'] = "hidden";
        input['name'] = "do";
        input['value'] = "true";
        form.appendChild(input);
        form.submit();
    }
</script>
<div id="titulo">Histórico</div>
<div style="font-size:14px">
    <p>O sistema deduziu o seu histórico. Caso queira acrescentar algo selecione as opções e envie... </p>
    <form id="matricula" action="Historico.do" method="POST">
        <table style="width:815px;">
            <c:if test="${msg != null}">
                <tr id="erro">
                    <td colspan="3">${msg}</td>
                </tr>
            </c:if>
            <tr class="titResult">
                    <td colspan="3">Disciplinas em que você foi aprovado</td>
            </tr>
            <c:forEach var="disciplinaAp" items="${disciplinasAprovadas}">
                <tr class="result">
                    <td><b>(${disciplinaAp.codDisciplina})</b></td>
                    <td>${disciplinaAp.nome}</td>
                </tr>              
            </c:forEach> 
            <tr class="titResult">
                    <td colspan="3">Disciplinas que você não cursou ou que foi reprovado</td>
            </tr>
            <c:forEach var="disciplinaDe" items="${disciplinasDesconhecido}">
                <tr class="result">
                    <td width="60"><b>(${disciplinaDe.codDisciplina})</b></td>
                    <td width="300">${disciplinaDe.nome}</td>
                    <td><input type="checkbox" name="selecionadasDesconhecido" value="${disciplinaDe.idDisciplina}" 
                                <c:if test="${disciplinasSelecionadas[disciplinaDe.idDisciplina]}">
                                   disabled="disabled"
                                </c:if> /> fui aprovado </td>
                </tr>              
            </c:forEach>  
            <tr class="titResult">
                    <td colspan="3">Disciplinas que você está cursando</td>
            </tr>
            <c:forEach var="disciplinaAn" items="${disciplinasAndamento}">
                <tr class="result">
                    <td width="60"><b>(${disciplinaAn.codDisciplina})</b></td>
                    <td width="300">${disciplinaAn.nome}</td>
                    <td><input type="checkbox" name="selecionadasAprovadas" value="${disciplinaAn.idDisciplina}" 
                                <c:if test="${disciplinasSelecionadas[disciplinaAn.idDisciplina]}">
                                    checked="checked"
                                </c:if> /> fui aprovado</td>
                </tr>              
            </c:forEach>   
            <tr>
                <td align="right" colspan="3"><input type="submit" value="enviar" /><input type="reset" value="limpar" /></td>
            </tr>
        </table>    
    </form>
</div>