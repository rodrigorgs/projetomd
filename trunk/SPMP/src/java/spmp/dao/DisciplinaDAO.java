/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.dao;

import java.util.List;
import spmp.bean.Disciplina;
import spmp.bean.SemestreSugerido;

/**
 *
 * @author Stefani Pires
 */
public interface DisciplinaDAO {

    public List<Disciplina> getDisciplinas() throws DAOException;
    public Disciplina getDiciplina(String id) throws DAOException;
    
    public List<SemestreSugerido> getDisciplinasBySemestre() throws DAOException;
}
