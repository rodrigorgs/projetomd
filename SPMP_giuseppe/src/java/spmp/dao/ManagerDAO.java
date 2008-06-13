/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.dao;

import spmp.dao.data.AlunoData;
import spmp.dao.data.DisciplinaData;
import spmp.dao.data.MatriculaData;
import spmp.dao.data.TurmaData;

/**
 *
 * @author Stefani Pires
 */
public class ManagerDAO implements DAOFactory {

    private static ManagerDAO instance;


    private ManagerDAO() {}

    
    public static ManagerDAO getInstance() {
        if (instance == null) {
            instance = new ManagerDAO();
        }
        return instance;
    }

    public AlunoDAO createAlunoDAO() {
        return new AlunoData();
    }
    
    public DisciplinaDAO createDiciplinaDAO() {
        return new DisciplinaData();
    }
    
    public TurmaDAO createTurmaDAO() {
        return new TurmaData();
    }

    public MatriculaDAO createMatriculaDAO() {
        return new MatriculaData();
    }

}
