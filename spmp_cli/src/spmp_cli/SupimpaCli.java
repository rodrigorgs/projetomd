package spmp_cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import jpl.Compound;
import jpl.JPL;
import jpl.Query;
import jpl.Term;
import jpl.Util;

/**
 * Interface textual para o SPMP.
 * 
 * Instruções:
 *   Para adicionar uma disciplina na pre-matricula: a fis121<Enter>
 *   Para remover uma disciplina na pre-matricula: r fis121<Enter>
 *   Para avancar para a proxima tela: <Enter>
 *   Para sair do programa: q<Enter>
 * 
 * O código atual não armazena nenhum fato. Fica tudo na memória e se perde
 * quando o programa encerra.
 * 
 * Veja linhas marcadas com TODO.
 * 
 * Guia rapido de JPL:
 * - Query.hasSolution executa a consulta e retorna o valor retornado pelo
 * Prolog (true ou false).
 * - Query.allSolutions retorna todas as solucoes de uma consulta (no caso de
 * ser uma consulta com variaveis). Cada solucao e' um Hashtable cujas chaves
 * sao os nomes das variaveis.
 * 
 * @author rodrigo
 */
public class SupimpaCli {

    public static void assertFact(Compound fact) {
        Query q = new Query("assert", fact);
        if (!q.hasSolution())
            throw new RuntimeException("ERROR: could not assert");
        // TODO: inserir no banco de dados
    }

    public static void retractFact(Compound fact) {
        Query q = new Query("retract", fact);
        if (!q.hasSolution())
            throw new RuntimeException("ERROR: could not retract");
        //TODO: remover do banco de dados. Deve tratar termos do tipo Variable.
    }

    /**
     * Compoe o predicado fornecido com os termos fornecidos e retorna um
     * predicado do prolog.
     * Exemplo: comp("filho", "joao", "maria") retorna o predicado
     * filho(joao, maria).
     * @param pred
     * @param attr
     * @return
     */
    public static Compound comp(String pred, String ... attr) {
        int n = attr.length;
        Term terms[] = new Term[n];
        for (int i = 0; i < n; i++)
            terms[i] = Util.textToTerm(attr[i]);
        
        return new Compound(pred, terms);
    }
    
    public static void loadData() {
        JPL.init();
        boolean success = Query.hasSolution(comp("consult", "'../prolog/main.pl'"));
        if (!success)
            throw new RuntimeException("Could not load prolog file");

        // TODO: carregar os fatos a partir do banco de dados
        // (apenas as regras serao carregadas de um arquivo pl)
    }
    
    public static String[] getVar(String var, Hashtable[] solutions) {
        int n = solutions.length;
        String[] ret = new String[n];
        
        for (int i = 0; i < n; i++)
           ret[i] = solutions[i].get(var).toString();

        return ret;
    }
    
    public static void print(Object[] a) {
        for (Object x : a)
            System.out.print(x.toString() + ", ");
        System.out.println();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Throwable {
        loadData();
        
        String aluno = Prompt.prompt("Digite seu nome de usuario: ");
    
        System.out.println("Sistema de Planejamento de Matricula com Prolog");
        System.out.println("-----------------------------------------------");
        
        while (true) {
            boolean stop = false;
            while (!stop) {
                System.out.println("\n== Pré-Matrícula ==\n");
                System.out.print("Nao pode se matricular: ");
                print(getVar("D", Query.allSolutions(comp("pode_se_matricular_disc", aluno, "D", "false"))));
                System.out.print("Pode se matricular: ");
                print(getVar("D", Query.allSolutions(comp("pode_se_matricular_disc", aluno, "D", "true"))));

                String line = Prompt.prompt();

                if (line.isEmpty())
                    stop = true;
                else {
                    String[] tokens = line.split("  *");
                    String cmd = tokens[0];
                    String disc = tokens[1];
                    Compound fact = comp("pre_matricula", aluno, disc, "presente");

                    if (cmd.equals("a")) {
                        if (!Query.hasSolution(comp("pode_se_matricular_disc", aluno, disc, "false")))
                            assertFact(fact);
                        else 
                            System.out.println("ERRO");
                    }
                    else if (cmd.equals("r")) {
                        if (Query.hasSolution(fact))
                            retractFact(fact);
                        else 
                            System.out.println("ERRO");
                    }    
                }
            }

            stop = false;
            while (!stop) {
                System.out.println("\n== Matricula ==\n");
                String[] turmas = getVar("T", Query.allSolutions(
                        comp("turmas_para_aluno", aluno, "T")));
                for (String turma : turmas) {
                    System.out.print(turma + " ");
                    Hashtable solutions[] = Query.allSolutions(comp("horario", turma, "Dia", "T1", "T2"));
                    for (Hashtable sol : solutions)
                        System.out.printf("[%s %s-%s] ", sol.get("Dia"), sol.get("T1"), sol.get("T2"));
                    System.out.println();
                }

                System.out.println("Digite as turmas escolhidas separados por espaços");
                String line = Prompt.prompt();

                String[] escolhidas = line.split("  *");

                //TODO: modularizar assert/rollback em um metodo.
                boolean rollback = false;
                stop = true;
                for (String turma : escolhidas) {
                    if (Query.hasSolution(comp("pode_se_matricular_turma", aluno, turma, "false"))) {
                        rollback = true;
                        stop = false;
                        break;
                    }
                    else {
                        assertFact(comp("matricula", aluno, turma));
                    }
                }

                if (rollback) {
                    System.out.println("ERRO: selecao invalida.");
                    retractFact(comp("matricula", aluno, "_"));
                }
            }

            System.out.println("\n== Resultado ==\n");
            String[] discs = getVar("D", Query.allSolutions(comp("matricula_disc", aluno, "D")));
            for (String disc : discs) {
                System.out.println();
                String line = Prompt.prompt("Você foi aprovado(a) em " + disc + " (s/n)? ");
                boolean resp = line.equalsIgnoreCase("s");
                assertFact(comp("foi_aprovado", aluno, disc, "" + resp));
            }
            
            // remove informacao de matricula
            retractFact(comp("matricula", aluno, "_"));
            // Envelhece informacao de pre-matricula.
            String[] discspre = getVar("D", Query.allSolutions(comp("pre_matricula", aluno, "D", "presente")));
            for (String disc : discspre) {
                retractFact(comp("pre_matricula", aluno, disc, "presente"));
                assertFact(comp("pre_matricula", aluno, disc, "passado"));
            }
            //TODO: retracts
        }
    }

}

class Prompt {
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
    public static String prompt() throws IOException {
        return prompt(null);
    }
    
    public static String prompt(String msg) throws IOException {
        System.out.print(msg == null ? "? " : msg);
        String line = in.readLine();
        
        if (line.equals("q"))
            System.exit(1);

        return line;
    }
}
