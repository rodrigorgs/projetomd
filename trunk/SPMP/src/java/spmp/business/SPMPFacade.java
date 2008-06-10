/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.business;

import java.util.HashMap;
import java.util.List;
import spmp.bean.Aluno;
import spmp.bean.Disciplina;
import spmp.bean.Turma;

/**
 * Ponto de acesso global as funcionalidades do sistema.
 * 
 * @author Giuseppe Lima - giuseppeanl@gmail.com
 * @author Stefani Pires - stefani.pires@gmail.com
 * @author Rodrigo Rocha - rodrigorgs@gmail.com
 */
public class SPMPFacade {
    
    private static SPMPFacade instance;
    
    private SPMPFacade() {
        
    }
    
    public static SPMPFacade getInstance() {
        if (instance == null)
           instance = new SPMPFacade();
        return instance;
    }
    
    /**
     * Retorna as disciplinas por semestre num HashMap (key = semestre, value=lista de disciplinas
     * do referente semestre).
     * @param aluno
     * @return
     */
    public HashMap<Integer, List<Disciplina>> getTodasDisciplinasPorSemestre() {
        return null;
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
