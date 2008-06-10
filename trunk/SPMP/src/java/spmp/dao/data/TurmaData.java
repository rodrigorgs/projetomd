/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spmp.dao.data;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import spmp.bean.Turma;
import spmp.dao.DAOException;
import spmp.dao.TurmaDAO;

/**
 *
 * @author Stefani Pires
 */
public class TurmaData implements TurmaDAO {

    private EntityManager em;

    /**
     * No args constructor. Obtains the JPA EntityManager.
     */
    public TurmaData() {
        em = Connection.getInstance().getEntityManager();
    }
    
    public List<Turma> getTurmaByDisciplina(String idDisciplina) throws DAOException {
         try {
            em.getTransaction().begin();
            Query query = em.createNamedQuery("Turma.findByIdDisciplina");
            List<Turma> list = query.setParameter("idDisciplina", idDisciplina).getResultList();
            em.getTransaction().commit();
            return list;

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DAOException(e.getMessage());
        }
    }

}
