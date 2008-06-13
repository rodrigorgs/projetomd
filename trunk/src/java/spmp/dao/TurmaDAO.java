/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.dao;

import java.util.List;
import spmp.bean.Turma;

/**
 *
 * @author Stefani Pires
 */
public interface TurmaDAO {
    
    public List<Turma> getTurmaByDisciplina(String idDisciplina) throws DAOException;

}
