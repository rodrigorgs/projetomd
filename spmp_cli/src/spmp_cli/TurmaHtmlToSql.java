/*
 * Criado em 06/11/2004
 */
package spmp_cli;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 */
public class TurmaHtmlToSql {
    public void newDisciplina(String codigo, String nome) {
        System.out.printf("INSERT INTO disciplina (idDisciplina, codDisciplina, nome) VALUES ('%s', '\\'%s\\'', '\\'%s\\'');\n", codigo.toLowerCase(), codigo, nome);
    }

    public void newTurma(String disc, String turma) {
        System.out.printf("INSERT INTO turma (idDisciplina, idTurma, codTurma) VALUES ('%s', '%s', '\\'%s\\'');\n", disc.toLowerCase(), disc.toLowerCase() + turma, turma);
    }

    public void newHorario(String disc, String turma, String dia, String horaIni, String horaFim) {
        System.out.printf("INSERT INTO horario (idTurma, diaSemana, horaInicio, horaFim) VALUES ('%s', '%s', %s, %s);\n", disc.toLowerCase() + turma, dia.toLowerCase(), horaIni, horaFim);
    }
    
    public void criaTurmas(InputStream in) throws IOException {
        BufferedReader arq = new BufferedReader(
                new InputStreamReader(in));

        final String regex = "<tr><td><FONT SIZE=2>(.*?)" + // materia
                "<td><FONT SIZE=2>(.*?)" + // turma
                "<td><FONT SIZE=2>(.*?)" + // vagas
                "<td><FONT SIZE=2>(.*?)" + // dia da semana
                "<td><FONT SIZE=2>(.*?)" + // horario
                "<td><FONT SIZE=2>(.*)";  // docente

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;


        String lastDia = null;
        String linha;
        String materiaCod = null;
        String materiaNome = null;
        int vagas;
        String turmaCod = null;
        String stringDia = null;
        String horaIni = null, horaFim = null;
        String stringMateria;
        String stringTurma;
        String stringHorario;

        while ((linha = arq.readLine()) != null) {
            matcher = pattern.matcher(linha);
            if (matcher.find()) {
                // materia
                stringMateria = matcher.group(1).trim();
                if (!stringMateria.equals("")) {
                    materiaCod = stringMateria.substring(0, 6);
                    materiaNome = stringMateria.substring(9);
                    newDisciplina( materiaCod, materiaNome);
                }

                // turma
                stringTurma = matcher.group(2).trim();
                if (!stringTurma.equals("")) {
                    vagas = Integer.parseInt(matcher.group(3));
                    turmaCod = stringTurma;
                    
                    newTurma( materiaCod,turmaCod);
                    //INSERT INTO disciplina (idDisciplina, codDisciplina, nome) VALUES ('let051', '\'LET051\'', '\'FRANCES INSTRUMENTAL I  N-100\'');
                }

                lastDia = stringDia;
                
                // novo dia de aula
                stringDia = matcher.group(4).trim();
                if (stringDia.equals(""))
                    stringDia = lastDia;

                // nova hora de aula (na pratica, nova aula)
                stringHorario = matcher.group(5).trim();
                if (!stringHorario.equals("")) {
                    horaIni = stringHorario.substring(0, 2);
                    horaFim = stringHorario.substring(9, 11);
                }

                newHorario( materiaCod, turmaCod,stringDia, horaIni, horaFim);
//                String docente = matcher.group(6);
            }
        }
    }
    
    public static void main(String[] args) throws Throwable {
        InputStream in = new FileInputStream("../prolog/turmas.html");
        TurmaHtmlToSql m = new TurmaHtmlToSql();
        m.criaTurmas(in);
    }
}
