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
    
    private AlunoBusiness(){
        ManagerDAO managerDAO = ManagerDAO.getInstance();
        alunoDAO = managerDAO.createAlunoDAO();
    }
    
    public void insert(String id, String nome, String email, String senha) throws BusinessException{
        try {
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
}
