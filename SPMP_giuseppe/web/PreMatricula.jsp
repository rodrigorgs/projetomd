<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    function doCLick(){
        document.getElementById('prematricula').submit();
    }
    function doPostEfetivo(){
        form = document.getElementById('prematricula');
        input = document.createElement("input");
        input['type'] = "hidden";
        input['name'] = "do";
        input['value'] = "true";
        form.appendChild(input);
        form.submit();
    }
</script>
<div id="titulo">Pré-Matrícula</div>
<div style="font-size:14px">
    <p>Escolha as disciplinas abaixo para efetuar a pré-matricula...</p>
    <form id="prematricula" action="PreMatricula.do" method="POST">
        <table style="width:815px;">
            <c:if test="${msg != null}">
                <tr id="erro">
                    <td colspan="4">${msg}</td>
                </tr>
            </c:if>
            <c:forEach var="entry" items="${disciplinasDisponiveis}" begin="1">
                <c:if test="${entry.key != 0}">
                    <tr class="titResult">
                        <td colspan="4">${entry.key}º Semestre</td>
                    </tr>
                    <c:forEach var="disciplina" items="${disciplinasDisponiveis[entry.key]}">
                        <tr class="result">
                            <td width="10"><input type="checkbox" name="disciplinasSelecionadas" value="${disciplina.idDisciplina}" 
                                       <c:if test="${disciplinasSelecao[disciplina.idDisciplina]}"> checked="checked" </c:if> />
                                       </td>                   
                            <td width="100"><b><c:if test="${invalidas[disciplina]}">***</c:if>(${disciplina.codDisciplina})</b>
                            <td width="300">${disciplina.nome}</td>
                            <td style="font-size:12px">
                                <c:forEach var="disciplinaRequisitos" items="${disciplinasPreRequisitos[disciplina.idDisciplina]}">
                                    <abbr title="${disciplinaRequisitos.nome}">${disciplinaRequisitos.codDisciplina}</abbr>
                                </c:forEach>                               
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
            </c:forEach>    
            <c:forEach var="entry" items="${disciplinasDisponiveis}" begin="0" end="0">
                
                    <tr class="titResult">
                        <td colspan="4">Optativas </td>
                    </tr>
                    <c:forEach var="disciplina" items="${disciplinasDisponiveis[entry.key]}">
                        <tr class="result">
                            <td><input type="checkbox" name="disciplinasSelecionadas" value="${disciplina.idDisciplina}" 
                                       <c:if test="${disciplinasSelecao[disciplina.idDisciplina]}"> checked="checked" </c:if> />
                                       </td>                   
                            <td><b>(${disciplina.codDisciplina})</b>
                            <td>${disciplina.nome}</td>
                            <td style="font-size:12px">
                                <c:forEach var="disciplinaRequisitos" items="${disciplinasPreRequisitos[disciplina.idDisciplina]}">
                                    <abbr title="${disciplinaRequisitos.nome}">${disciplinaRequisitos.codDisciplina}</abbr>
                                </c:forEach>                               
                            </td>
                        </tr>
                        
                    </c:forEach>
            </c:forEach>   
            <tr>
                <td align="right" colspan="4"><input type="submit" value="efetuar pré-matrícula" /><input type="reset" value="limpar" /></td>
            </tr>
        </table>    
    </form>
    
</div>