/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.dao;

import java.util.List;
import spmp.bean.PreMatricula;

/**
 *
 * @author Stefani Pires
 */
public interface PreMatriculaDAO {
    
    public void insert(PreMatricula preMatricula) throws DAOException;
    public void delete(PreMatricula preMatricula) throws DAOException;
    
    public List<PreMatricula> getPreMatriculaByAluno(String idAluno) throws DAOException;
            

}
