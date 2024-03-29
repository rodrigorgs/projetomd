/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.oldbusiness.prolog;

import java.util.List;
import jpl.Compound;
import spmp.bean.Disciplina;
import spmp.bean.SemestreSugerido;
import spmp.business.PrologUtil;
import spmp.dao.DAOException;
import spmp.dao.DisciplinaDAO;

/**
 *
 * @author roden
 */
public class DisciplinaFact implements DisciplinaDAO {

    public void insertDisciplina(Disciplina disc) {
        Compound fact = PrologUtil.comp("disciplina", 
                disc.getIdDisciplina(),
                disc.getCodDisciplina(),
                disc.getNome());
        PrologUtil.assertFactOnProlog(fact);
    }
    
    public List<Disciplina> getDisciplinas() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Disciplina getDiciplina(String id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<SemestreSugerido> getDisciplinasBySemestre() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
