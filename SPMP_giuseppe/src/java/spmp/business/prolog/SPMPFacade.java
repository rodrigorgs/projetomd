/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.business.prolog;

import java.sql.SQLException;
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
import spmp.bean.SemestreSugerido;
import spmp.business.BusinessException;
import spmp.business.MatriculaException;
import spmp.business.PreMatriculaException;
import spmp.business.ValidaUtils;

/**
 * Ponto de acesso global as funcionalidades do sistema.
 * 
 * @author Giuseppe Lima - giuseppeanl@gmail.com
 * @author Stefani Pires - stefani.pires@gmail.com
 * @author Rodrigo Rocha - rodrigorgs@gmail.com
 */
public class SPMPFacade {
    public enum SituacaoEnum { EM_ANDAMENTO, APROVADO, DESCONHECIDO }
    private static SPMPFacade instance;
    private PrologData data = null;
    
//    private static final String discVar[] = { "Id", "Cod", "Nome" };
    
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

    private static String quote(String s) {
        return "'" + s + "'";
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
    
    private static Compound comp(String pred, String ... attr) {
        return PrologUtil.comp(pred, attr);
    }
    
    private static Disciplina newDisciplina(String discId) {
        Hashtable solDisc = Query.oneSolution(comp("disciplina", discId, "Cod", "Nome"));
        return new Disciplina(unquote(discId), unquote(solDisc.get("Cod")), unquote(solDisc.get("Nome")));
    }
    
    private static Turma newTurma(String turmaId) {
        Hashtable solTurma = Query.oneSolution(comp("turma", "Disc", turmaId, "Cod"));
        Turma turma = new Turma(turmaId, unquote(solTurma.get("Cod")));
        turma.setIdDisciplina(newDisciplina(unquote(solTurma.get("Disc"))));
        turma.setHorarioCollection(new ArrayList<Horario>());
        
        Hashtable solutions[] = Query.allSolutions(comp("horario", turmaId, "Dia", "T1", "T2"));
        for (Hashtable sol : solutions) {
            String dia = unquote(sol.get("Dia"));
            int t1 = Integer.parseInt(unquote(sol.get("T1")));
            int t2 = Integer.parseInt(unquote(sol.get("T2")));

            Horario h = new Horario(turmaId, dia, t1, t2);
            turma.getHorarioCollection().add(h);
        }        
        
        return turma;
    }
    
    /**
     * Retorna as disciplinas por semestre num HashMap (key = semestre, value=lista de disciplinas
     * do referente semestre), excluindo da lista as disciplinas que o aluno
     * nao pode escolher.
     * @param aluno
     * @return
     */
    public HashMap<Integer, List<Disciplina>> getDisciplinasPorSemestre(Aluno aluno) {
        HashMap<Integer, List<Disciplina>> map = new HashMap<Integer, List<Disciplina>>();
        Hashtable[] results = Query.allSolutions(comp("get_disciplinas", aluno.getIdAluno(), "Semestre", "Id", "Cod", "Nome"));
        for (Hashtable item : results) {
            int semestre = Integer.parseInt(unquote(item.get("Semestre")));
            
            if (map.get(semestre) == null)
                map.put(semestre, new ArrayList<Disciplina>());
            List list = map.get(semestre);
                    
            Disciplina disciplina = new Disciplina();
            disciplina.setIdDisciplina(unquote(item.get("Id")));
            disciplina.setCodDisciplina(unquote(item.get("Cod")));
            disciplina.setNome(unquote(item.get("Nome")));
            disciplina.setSemestreSugerido(new SemestreSugerido(disciplina.getIdDisciplina(), semestre));
            list.add(disciplina);
        }        
        return map;
    }
    
    public List<Disciplina> getPrerequisitos(Disciplina disc) {
        ArrayList<Disciplina> ret = new ArrayList<Disciplina>();
        
        Compound query = comp(
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
 
    public HashMap<Disciplina, List<Turma>> getTurmasParaAluno(Aluno aluno) {
        HashMap<Disciplina, List<Turma>> ret = new HashMap<Disciplina, List<Turma>>();
        
        Hashtable[] turmas = Query.allSolutions(
                comp("get_turmas_para_aluno", aluno.getIdAluno(), "Turma", "Disciplina", "Cod"));
        
        for (Hashtable t : turmas) {
            String turmaId = unquote(t.get("Turma"));
            Turma turma = newTurma(turmaId);
            Disciplina disc = turma.getIdDisciplina();
            if (!ret.containsKey(disc))
                ret.put(disc, new ArrayList<Turma>());
            ret.get(disc).add(turma);
        }
            
        return ret;
    }
    
    public String[] getDisciplinasPreMatricula(Aluno aluno) {
        return PrologUtil.getVar("D", Query.allSolutions(comp("pre_matricula", aluno.getIdAluno(), "D", "presente")));
    }
    
    public String[] getTurmasMatricula(Aluno aluno) {
        return PrologUtil.getVar("T", Query.allSolutions(comp("matricula", aluno.getIdAluno(), "T")));
    }
    
    /**
     * Efetua a pré-matrícula.
     * Se houver algum problema na seleção de disciplinas escolhidas para a
     * pré-matrícula, dispara uma exceção contendo a lista de disciplinas
     * inválidas.
     * @param disciplinas
     */
    public void efetuarPreMatricula(Aluno aluno, String[] disciplinaIds) throws BusinessException {
        String strAluno = aluno.getIdAluno();

        ArrayList<Disciplina> discInvalidas = new ArrayList<Disciplina>();
        ArrayList<Disciplina> discValidas = new ArrayList<Disciplina>();
        try {
            boolean selecaoValida = true;
            // retract da pre-matricula anterior
            data.retractFact(comp("pre_matricula", strAluno, "_", "presente"));
            // assert da pre-matricula atual
            for (String strDisc : disciplinaIds) {
                if (Query.hasSolution(comp(
                        "pode_se_matricular_disc", strAluno, strDisc, "false"))) {
                    selecaoValida = false;
                    discInvalidas.add(newDisciplina(strDisc));
                }
                else {
                    discValidas.add(newDisciplina(strDisc));
                    data.assertFact(comp("pre_matricula", strAluno, strDisc, "presente"));
                }
            }
            
            if (!selecaoValida) {
                data.retractFact(comp("pre_matricula", strAluno, "_", "presente"));
                throw new PreMatriculaException("Seleção inválida.", discValidas, discInvalidas);
            }
            
            // apaga toda a informação sobre matrícula
            data.retractFact(comp("matricula", strAluno, "_"));
        } catch (SQLException e) {
            throw new BusinessException(e.getMessage());
        }
    }
    
    /**
     * Efetua a matrícula em uma disciplina numa turma.
     * @param disciplina
     * @param turma
     */
    public void efetuarMatricula(Aluno aluno, String[] turmaIds) throws BusinessException {
        String strAluno = aluno.getIdAluno();
        ArrayList<Turma> invalidas = new ArrayList<Turma>();
        ArrayList<Turma> validas = new ArrayList<Turma>();
        try {
            boolean selecaoValida = true;
            // retract da matricula anterior
            data.retractFact(comp("matricula", strAluno, "_"));
            // assert da matricula atual
            for (String strTurma : turmaIds) {
                if (Query.hasSolution(comp(
                        "pode_se_matricular_turma", strAluno, strTurma, "false"))) {
                    selecaoValida = false;
                    invalidas.add(newTurma(strTurma));
                }
                else {
                    validas.add(newTurma(strTurma));
                    data.assertFact(comp("matricula", strAluno, strTurma));
                }
            }

            if (!selecaoValida) {
                data.retractFact(comp("matricula", strAluno, "_"));
                throw new MatriculaException("Seleção inválida.", validas, invalidas);
            }
        } catch (SQLException e) {
            throw new BusinessException(e.getMessage());
        }
    }
    
    public HashMap<SituacaoEnum, List<Disciplina>> getHistorico(Aluno aluno) {
        HashMap ret = new HashMap<SituacaoEnum, List<Disciplina>>();
        List<Disciplina> retEmAndamento = new ArrayList<Disciplina>();
        ret.put(SituacaoEnum.EM_ANDAMENTO, retEmAndamento);
        List<Disciplina> retAprovado = new ArrayList<Disciplina>();
        ret.put(SituacaoEnum.APROVADO, retAprovado);
        List<Disciplina> retDesconhecido = new ArrayList<Disciplina>();
        ret.put(SituacaoEnum.DESCONHECIDO, retDesconhecido);
        
        String strAluno = aluno.getIdAluno();
        
        String[] emAndamento = PrologUtil.getVar("D", Query.allSolutions(comp("matricula_disc", strAluno, "D")));
        for (String disc : emAndamento)
            retEmAndamento.add(newDisciplina(disc));
        
        String[] aprovado = PrologUtil.getVar("D", Query.allSolutions(comp("foi_aprovado", strAluno, "D", "true")));
        for (String disc : aprovado)
            retAprovado.add(newDisciplina(disc));        
        
        String[] desconhecido = PrologUtil.getVar("D", Query.allSolutions(comp("get_desconhecido", strAluno, "D")));
        for (String disc : desconhecido)
            retDesconhecido.add(newDisciplina(disc));
        
        return ret;
    }
    
    /** ATENCAO: assumimos que a interface nao vai passar um resultado invalido
     * (por exemplo, dizer que o aluno foi aprovado em uma disciplina que ele
     * nao pode ter cursado).
     * @param aluno
     * @param disciplinasIdAprovado
     * @throws spmp.business.BusinessException
     */
    public void setResultado(Aluno aluno, String[] disciplinasIdAprovado) throws BusinessException {
        try {
            for (String disc : disciplinasIdAprovado)
                data.assertFact(comp("historico", aluno.getIdAluno(), disc, "true"));
            proximoSemestre(aluno);
        } catch (SQLException e) {
            throw new BusinessException(e.getMessage());
        }
    }
    
    public void proximoSemestre(Aluno aluno) throws BusinessException {
        try {
            String strAluno = aluno.getIdAluno();
            // remove informacao de matricula
            data.retractFact(comp("matricula", strAluno, "_"));
            
            // Envelhece informacao de pre-matricula.
            String[] discspre = PrologUtil.getVar("D", Query.allSolutions(comp("pre_matricula", strAluno, "D", "presente")));
            for (String disc : discspre) {
                data.retractFact(comp("pre_matricula", strAluno, disc, "presente"));
                data.assertFact(comp("pre_matricula", strAluno, disc, "passado"));
            }
        } catch (SQLException e) {
            throw new BusinessException(e.getMessage());
        }
    }
    
    public void cadastrarAluno(String id, String nome, String email, String senha, String csenha) throws BusinessException {
        validaCadastroDeAluno(id, nome, email, senha, csenha);
        try {
            data.assertFact(comp("aluno", quote(id), quote(nome), quote(email), quote(senha)));
        } catch (SQLException e) {
            throw new BusinessException(e.getMessage());
        }
    }
    
    public Aluno login(String id, String senha) throws BusinessException {
        Hashtable sol = Query.oneSolution(comp("aluno", quote(id), "Nome", "Email", quote(senha)));
        if (sol == null)
            throw new BusinessException("Aluno não cadastrado.");
        
        return new Aluno(id,
                unquote(sol.get("Nome")),
                unquote(sol.get("Email")), 
                senha);
    }
    
    
    private void validaCadastroDeAluno(String id, String nome, String email, String senha, String csenha) throws BusinessException {
                String msg = "";
        if (!ValidaUtils.isFilled(nome)) {
            msg = "nome";
        }
        if (!ValidaUtils.isFilled(id)) {
            if (msg.length() > 0){
                msg += ", ";
            }
            msg += "id";
        }
        if (!ValidaUtils.isFilled(email)) {
            if (msg.length() > 0) {
                msg += ", ";
            }
            msg += "email";
        }                      
        if (!ValidaUtils.isFilled(senha)) {
            if (msg.length() > 0) {
                msg += ", ";
            }
            msg += "senha";
        }
        if (!ValidaUtils.isFilled(csenha)) {
            if (msg.length() > 0){
                msg += ", ";
            }
            msg += "confirmação de senha";
        }

        if (msg.length() > 0) {
            throw new BusinessException("Os seguintes campos devem ser preenchidos: " + msg);
        }

        if (!ValidaUtils.isValidEmail(email)) {
            throw new BusinessException("E-mail inválido. Ex.: a@ga.com");
        }

        if (Query.hasSolution(comp("aluno", id, "_", "_", "_"))) {
            msg = "id";
        }
        if (Query.hasSolution(comp("aluno", "_", "_", quote(email), "_"))) {
            if (msg.length() > 0) {
                msg += ", ";
            }
            msg += "e-mail";
        }

        if (msg.length() > 0) {
            throw new BusinessException("Já existe aluno cadastrado com o mesmo: "+msg);
        }

        if (!senha.equals(csenha)) {
            throw new BusinessException("A senha e a confirmação não conferem.");
        }
    }
}


//    /**
//     * Retorna as disciplinas completadas pelo aluno.
//     * @param aluno
//     * @return
//     */
//    public List<Disciplina> getDisciplinasCompletadasDoAluno(Aluno aluno) {
//        return null;
//    }
//    
//    /**
//     * Retorna as disciplinas incompletadas pelo aluno.
//     * @param aluno
//     * @return
//     */
//    public List<Disciplina> getDisciplinasIncompletadasDoAluno(Aluno aluno) {
//        return null;
//    }
//    
//    /**
//     * Retorna as disciplinas cuja completude/incompletude não pode ser determinada.
//     * @param aluno
//     * @return
//     */
//    public List<Disciplina> getDisciplinasEstadoDesconhecidoDoAluno(Aluno aluno) {
//        return null;
//    }
//    
//    /**
//     * Retorna as disciplinas em andamento do aluno.
//     * @param aluno
//     * @return
//     */
//    public List<Disciplina> getDisciplinasEmAndamentoDoAluno(Aluno aluno) {
//        return null;
//    }
//    /**
//     * Retorna o conflito entre um conjunto de disciplinas para que o usuário possa
//     * escolherna pré-matrícula.
//     * @return disciplinas conflitantes.
//     */
//    public List<Disciplina> existeConflito(Disciplina disciplina, List<Disciplina> disciplinas) {
//        return null;
//    }
//    /**
//     * Retorna as disciplinas por semestre num HashMap (key = semestre, value=lista de disciplinas
//     * do referente semestre), excluindo as disciplinas que o aluno não pode escolher.
//     * @param aluno
//     * @return
//     */
//    public HashMap<Integer, List<Disciplina>> getSugestaoDisciplinasPorSemestre(Aluno aluno) {
//        
//        return null;
//    }