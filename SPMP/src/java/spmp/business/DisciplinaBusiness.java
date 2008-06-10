/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.business;

import java.util.List;
import spmp.dao.DAOException;
import spmp.dao.DisciplinaDAO;
import spmp.dao.ManagerDAO;
import spmp.bean.Disciplina;

/**
 *
 * @author Stefani Pires
 */
public class DisciplinaBusiness {

    private static DisciplinaBusiness instance;
    private DisciplinaDAO disciplinaDAO;
    
    private DisciplinaBusiness() throws DAOException {
        ManagerDAO managerDAO = ManagerDAO.getInstance();
        disciplinaDAO = managerDAO.createDiciplinaDAO();
        
        List<Disciplina> discs = disciplinaDAO.getDisciplinas();
        // TODO
    }
    
    public static DisciplinaBusiness getInstance() throws DAOException {
        if (instance == null) {
            instance = new DisciplinaBusiness();
        }
        return instance;
    }
    
    // adicionar métodos
}
