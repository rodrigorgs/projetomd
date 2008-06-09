/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.dao;

/**
 *
 * @author Stefani Pires
 */
public interface DAOFactory {
    
    public AlunoDAO createAlunoDAO();
    public DisciplinaDAO createDiciplinaDAO();
    public TurmaDAO createTurmaDAO();
    public MatriculaDAO createMatriculaDAO();
    

}
