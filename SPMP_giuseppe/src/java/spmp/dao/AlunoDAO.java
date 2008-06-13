/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.dao;

import spmp.bean.Aluno;

/**
 *
 * @author Stefani Pires
 */
public interface AlunoDAO {

    public void insert(Aluno aluno) throws DAOException;
    public void update(Aluno aluno) throws DAOException;
    public void delete(Aluno aluno) throws DAOException;
    
    public Aluno getAlunoByIdSenha(String id, String senha);
    public Aluno getAlunoById(String id);
    public Aluno getAlunoByEmail(String id);
    
    
}
