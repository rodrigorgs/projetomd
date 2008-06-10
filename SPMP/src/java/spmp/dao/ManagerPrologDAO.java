/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.dao;

import spmp.business.prolog.DisciplinaFact;
import spmp.dao.data.AlunoData;
import spmp.dao.data.DisciplinaData;
import spmp.dao.data.MatriculaData;
import spmp.dao.data.TurmaData;

/**
 *
 * @author Stefani Pires
 */
public class ManagerPrologDAO implements DAOFactory {

    private static ManagerPrologDAO instance;


    private ManagerPrologDAO() {}

    
    public static ManagerPrologDAO getInstance() {
        if (instance == null) {
            instance = new ManagerPrologDAO();
        }
        return instance;
    }

    public AlunoDAO createAlunoDAO() {
        throw new UnsupportedOperationException("TODO: implement");
    }
    
    public DisciplinaDAO createDiciplinaDAO() {
        return new DisciplinaFact();
    }
    
    public TurmaDAO createTurmaDAO() {
        throw new UnsupportedOperationException("TODO: implement");
    }

    public MatriculaDAO createMatriculaDAO() {
        throw new UnsupportedOperationException("TODO: implement");
    }

}
