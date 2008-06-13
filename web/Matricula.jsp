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
<div id="titulo">Matrícula</div>
<div style="font-size:14px">
    <p>Escolha as turmas paras as disciplinas abaixo para efetuar sua matricula...</p>
    <form id="matricula" action="Matricula.do" method="POST">
        <table style="width:815px;">
            <c:if test="${msg != null}">
                <tr id="erro">
                    <td colspan="3">${msg}</td>
                </tr>
            </c:if>
            <c:forEach var="entry" items="${turmasDisponiveis}">
                <tr class="titResult">
                    <input type=hidden name="disciplinas" value="${entry.key.idDisciplina}"></input>
                    <td colspan="3">(${entry.key.codDisciplina}) ${entry.key.nome}</td>
                </tr>
                <c:forEach var="turma" items="${turmasDisponiveis[entry.key]}">
                    <tr class="result">
                        <td width="10">
                            <input type="radio" name="${entry.key.idDisciplina}" value="${turma.idTurma}" 
                                <c:if test="${turmasSelecao[turma.idTurma]}">
                                    checked="checked"
                                </c:if> />
                        </td>
                        <td width="50">${turma.codTurma}</td>
                        <td>
                            <c:forEach var="horario" items="${turma.horarioCollection}">
                               ${horario.horarioPK.diaSemana}(${horario.horarioPK.horaInicio}-${horario.horarioPK.horaFim}),
                            </c:forEach>
                        </td>
                    </tr>
                </c:forEach>
                <tr class="result">
                    <td><input type="radio" name="${entry.key.idDisciplina}" value="none" 
                    <c:if test="${turmasSelecao[entry.key.idDisciplina]}">
                         checked="checked"
                    </c:if> /></td>
                    <td colspan="3"><b>Não me matricular nesta disciplina</b></td>
                </tr>
                
            </c:forEach>    
            <tr>
                <td align="right" colspan="3"><input type="submit" value="matrícula" /><input type="reset" value="limpar" /></td>
            </tr>
        </table>    
    </form>
</div>