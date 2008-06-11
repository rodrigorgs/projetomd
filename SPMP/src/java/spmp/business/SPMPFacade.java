/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import jpl.Compound;
import jpl.Query;
import spmp.bean.Aluno;
import spmp.bean.Disciplina;
import spmp.bean.Turma;
import spmp.bean.Horario;

/**
 * Ponto de acesso global as funcionalidades do sistema.
 * 
 * @author Giuseppe Lima - giuseppeanl@gmail.com
 * @author Stefani Pires - stefani.pires@gmail.com
 * @author Rodrigo Rocha - rodrigorgs@gmail.com
 */
public class SPMPFacade {
    
    private static SPMPFacade instance;
    private PrologData data = null;
    
    private SPMPFacade(String fileRegras) {
        data = new PrologData(fileRegras);
        try {
            data.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static SPMPFacade getInstance(String fileRegras) {
        if (instance == null)
           instance = new SPMPFacade(fileRegras);
        return instance;
    }
    
    public static SPMPFacade getInstance() {
        if (instance == null)
            throw new RuntimeException("SPMPFacade not initialized! Please call getInstance(String fileRegras).");
        return instance;
    }
    
    /**
     * Tira as aspas simples que envolvem a String passada como parametro.
     * @param o O objeto é convertido para String.
     * @return
     */
    private static String unquote(Object o) {
        String s = o.toString();
        if (s.charAt(0) == '\'')
            return s.substring(1, s.length() - 1);
        else
            return s;
    }
    
    /**
     * Retorna as disciplinas por semestre num HashMap (key = semestre, value=lista de disciplinas
     * do referente semestre).
     * @param aluno
     * @return
     */
    public HashMap<Integer, List<Disciplina>> getTodasDisciplinasPorSemestre() {
        // TODO: dividir por semestre
        HashMap<Integer, List<Disciplina>> map = new HashMap<Integer, List<Disciplina>>();
        Hashtable[] results = Query.allSolutions(PrologUtil.comp("get_todas_disciplinas_por_semestre", "Semestre", "Id", "Cod", "Nome"));
        for (Hashtable item : results) {
            int semestre;
            Object getsemestre = map.get("Semestre");
            if (getsemestre == null)
                semestre = 0;
            else
                semestre = Integer.parseInt(unquote(getsemestre));
            
            if (map.get(semestre) == null)
                map.put(semestre, new ArrayList<Disciplina>());
            List list = map.get(semestre);
                    
            Disciplina disciplina = new Disciplina();
            disciplina.setIdDisciplina(unquote(item.get("Id")));
            disciplina.setCodDisciplina(unquote(item.get("Cod")));
            disciplina.setNome(unquote(item.get("Nome")));
            disciplina.setSemestreSugerido(semestre);
            list.add(disciplina);
        }        
        return map;
    }
    
    public List<Disciplina> getPrerequisitos(Disciplina disc) {
        ArrayList<Disciplina> ret = new ArrayList<Disciplina>();
        
        Compound query = PrologUtil.comp(
                "get_prerequisitos", disc.getIdDisciplina(), "Id", "Cod", "Nome");
        
//        System.err.println(query);
        Hashtable[] results = Query.allSolutions(query);
        
        for (Hashtable item : results) {
            ret.add(new Disciplina(
                    unquote(item.get("Id")),
                    unquote(item.get("Cod")),
                    unquote(item.get("Nome"))
                    ));
        }
        
        return ret;
    }
 
    public List<Turma> getTurmasParaAluno(Aluno aluno) {
        List<Turma> ret = new ArrayList<Turma>();
        
        Hashtable[] turmas = Query.allSolutions(
                PrologUtil.comp("turmas_para_aluno", aluno.getIdAluno(), "Turma", "Cod"));
        
        for (Hashtable t : turmas) {
            String turmaId = unquote(t.get("Turma"));
            Turma turma = new Turma(turmaId, unquote(t.get("Cod")));
            turma.setHorarioCollection(new ArrayList<Horario>());
            ret.add(turma);
            
            Hashtable solutions[] = Query.allSolutions(PrologUtil.comp("horario", turmaId, "Dia", "T1", "T2"));
            for (Hashtable sol : solutions) {
                String dia = unquote(sol.get("Dia"));
                int t1 = Integer.parseInt(unquote(sol.get("T1")));
                int t2 = Integer.parseInt(unquote(sol.get("T2")));

                Horario h = new Horario(turmaId, dia, t1, t2);
                turma.getHorarioCollection().add(h);
            }
        }
        
        return ret;
    }
    
    /**
     * Retorna as disciplinas por semestre num HashMap (key = semestre, value=lista de disciplinas
     * do referente semestre), excluindo as disciplinas que o aluno não pode escolher.
     * @param aluno
     * @return
     */
    public HashMap<Integer, List<Disciplina>> getSugestaoDisciplinasPorSemestre(Aluno aluno) {
        
        return null;
    }
    
    /**
     * Retorna o conflito entre um conjunto de disciplinas para que o usuário possa
     * escolherna pré-matrícula.
     * @return disciplinas conflitantes.
     */
    public List<Disciplina> existeConflito(Disciplina disciplina, List<Disciplina> disciplinas) {
        return null;
    }
    
    /**
     * Efetua a pré-matrícula.
     * @param disciplinas
     */
    public void efetuarPreMatricula(List<Disciplina> disciplinas) {
        
    }
    
    /**
     * Efetua a matrícula em uma disciplina numa turma.
     * @param disciplina
     * @param turma
     */
    public void efetuarMatricula(Disciplina disciplina, Turma turma) {
        
    }
    
    /**
     * Retorna as disciplinas completadas pelo aluno.
     * @param aluno
     * @return
     */
    public List<Disciplina> getDisciplinasCompletadasDoAluno(Aluno aluno) {
        return null;
    }
    
    /**
     * Retorna as disciplinas incompletadas pelo aluno.
     * @param aluno
     * @return
     */
    public List<Disciplina> getDisciplinasIncompletadasDoAluno(Aluno aluno) {
        return null;
    }
    
    /**
     * Retorna as disciplinas cuja completude/incompletude não pode ser determinada.
     * @param aluno
     * @return
     */
    public List<Disciplina> getDisciplinasEstadoDesconhecidoDoAluno(Aluno aluno) {
        return null;
    }
    
    /**
     * Retorna as disciplinas em andamento do aluno.
     * @param aluno
     * @return
     */
    public List<Disciplina> getDisciplinasEmAndamentoDoAluno(Aluno aluno) {
        return null;
    }
    
}
