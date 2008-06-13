package spmp.business.prolog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import spmp.bean.Aluno;
import spmp.bean.Disciplina;
import spmp.business.BusinessException;
import spmp.bean.Horario;
import spmp.bean.Turma;

public class SupimpaCli {
    SPMPFacade facade = null;
    Aluno aluno = null;
    
    public SupimpaCli() {
        facade = SPMPFacade.getInstance("web/regras.pl");
    }

    public void run() {
        login();
        while (true) {
            preMatricula();
            matricula();
            resultado();
        }
    }
    
    public Aluno login() {
        String id = null, senha = null;
        
        do {
            id = Prompt.prompt("Digite seu nome de usuario: ");
            senha = Prompt.prompt("Digite sua senha: ");
            try {
                aluno = facade.login(id, senha);
            } catch (BusinessException e) {
                System.out.println(e.getMessage());
            }
        } while (aluno == null);
                
        return aluno;
    }

    public void printDisciplina(Disciplina disc) {
        System.out.printf("%s - %s\n", disc.getCodDisciplina(), disc.getNome());
    }
    
    public void printDisciplinas() {
        HashMap<Integer, List<Disciplina>> map = facade.getDisciplinasPorSemestre(aluno);
        
        for (int semestre : map.keySet()) {
            System.out.println("\n=== Semestre " + semestre);
            for (Disciplina disc : map.get(semestre)) {
                printDisciplina(disc);
            }
        }
        System.out.println();
    }
    
    public void preMatricula() {
        printDisciplinas();
        
        boolean done = true;
        do {
            done = true;
            try {
                System.out.println("Digite os codigos das disciplinas (em minusculas) separados por espaços");
                String line = Prompt.prompt();
                if (line.isEmpty())
                    return;
                String[] escolhidas = line.split("  *");

                facade.efetuarPreMatricula(aluno, escolhidas);
                
            } catch (BusinessException e) {
                System.out.println(e.getMessage());
                done = false;
            }
        } while (!done);
    }
    
    public void printTurmas() {
        
//        HashMap<Disciplina, List<Turma>> map = facade.getTurmasParaAluno(aluno);
//        for (Disciplina disc : map.keySet()) {
//            for (Turma t : turmas) {
//                System.out.printf("[%s] %s T%s\n", t.getIdTurma(), t.getIdDisciplina().getCodDisciplina(), t.getCodTurma());
//                for (Horario h : t.getHorarioCollection())
//                    System.out.printf("  %s: %s-%s\n", h.getHorarioPK().getDiaSemana(), h.getHorarioPK().getHoraInicio(), h.getHorarioPK().getHoraFim());
//            }
//        }
    }
    
    public void matricula() {
        printTurmas();
        
        boolean done = true;
        do {
            done = true;
            try {        
                System.out.println("Digite os ids das turmas, separados por espaços");
                String line = Prompt.prompt();
                if (line.isEmpty())
                    return;
                String[] escolhidas = line.split("  *");

                facade.efetuarMatricula(aluno, escolhidas);
            } catch (BusinessException e) {
                System.out.println(e.getMessage());
                done = false;
            }
        } while (!done);
    }
    
    public void printHistorico() {
        HashMap<SPMPFacade.SituacaoEnum, List<Disciplina>> hist = facade.getHistorico(aluno);
        
        for (SPMPFacade.SituacaoEnum situacao : hist.keySet()) {
            System.out.println("=== " + situacao);
            List<Disciplina> disciplinas = hist.get(situacao);
            for (Disciplina d : disciplinas)
                printDisciplina(d);
        }
        System.out.println();
    }
    
    public void resultado() {
        printHistorico();
        
        System.out.println("Digite os ids das disciplinas em que voce foi aprovado, separados por espaços");
        String line = Prompt.prompt();
        if (line.isEmpty())
            return;
        String[] escolhidas = line.split("  *");
        
        try {
            facade.setResultado(aluno, escolhidas);
        } catch (BusinessException e) {
            System.out.println(e.getMessage());
        }
                
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Throwable {
        SupimpaCli cli = new SupimpaCli();
        cli.run();
    }
}

class Prompt {
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
    public static String prompt() {
        return prompt(null);
    }
    
    public static String prompt(String msg) {
        try {
            System.out.print(msg == null ? "? " : msg);
            String line = in.readLine();

            if (line.equals("q"))
                System.exit(1);

            return line;
        } catch (IOException e) {
            return "";
        }
    }
}
