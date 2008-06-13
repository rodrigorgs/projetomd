/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package spmp.business;

import spmp.bean.Aluno;
import spmp.dao.AlunoDAO;
import spmp.dao.DAOException;
import spmp.dao.ManagerDAO;

/**
 *
 * @author Stefani Pires
 */
public class AlunoBusiness {

    private static AlunoBusiness instance;
    private AlunoDAO alunoDAO;

    public static AlunoBusiness getInstance() {
        if (instance == null) {
            instance = new AlunoBusiness();
        }
        return instance;
    }

    private AlunoBusiness() {
        ManagerDAO managerDAO = ManagerDAO.getInstance();
        alunoDAO = managerDAO.createAlunoDAO();
    }

    public void insert(String id, String nome, String email, String senha, String csenha) throws BusinessException {
        try {
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

            Aluno alunoID = alunoDAO.getAlunoById(id);
            Aluno alunoEmail = alunoDAO.getAlunoByEmail(email);
                
            if (alunoID != null) {
                msg = "id";
            }
            if (alunoEmail != null) {
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
            
            Aluno aluno = new Aluno();
            aluno.setIdAluno(id);
            aluno.setNome(nome);
            aluno.setEmail(email);
            aluno.setSenha(senha);

            alunoDAO.insert(aluno);
        } catch (DAOException ex) {
            throw new BusinessException(ex.getMessage());
        }

    }

    public Aluno login(String login, String senha) throws BusinessException {
        Aluno aluno = alunoDAO.getAlunoByIdSenha(login, 
                senha);
        if (aluno == null)  
            throw new BusinessException("Aluno não cadastrado.");
        return aluno;
        
    }
}
