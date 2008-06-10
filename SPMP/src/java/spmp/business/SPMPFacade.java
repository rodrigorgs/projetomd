/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import jpl.Query;
import spmp.bean.Aluno;
import spmp.bean.Disciplina;
import spmp.bean.Turma;
import spmp.dao.data.PrologData;
import spmp.business.prolog.Prolog;

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
    
    private SPMPFacade() {
        data = new PrologData();
        try {
            data.load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static SPMPFacade getInstance() {
        if (instance == null)
           instance = new SPMPFacade();
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
        map.put(0, new ArrayList<Disciplina>());
        Hashtable[] results = Query.allSolutions(Prolog.comp("disciplina", "Id", "Cod", "Nome"));
        for (Hashtable item : results) {
            Disciplina disciplina = new Disciplina();
            disciplina.setIdDisciplina(unquote(item.get("Id")));
            disciplina.setCodDisciplina(unquote(item.get("Cod")));
            disciplina.setNome(unquote(item.get("Nome")));
            map.get(0).add(disciplina);
        }
        return map;
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
